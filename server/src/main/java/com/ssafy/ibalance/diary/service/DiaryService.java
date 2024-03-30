package com.ssafy.ibalance.diary.service;

import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.child.exception.ChildAccessDeniedException;
import com.ssafy.ibalance.common.util.DtoConverter;
import com.ssafy.ibalance.common.util.FastAPIConnectionUtil;
import com.ssafy.ibalance.diary.dto.request.DiarySaveRequest;
import com.ssafy.ibalance.diary.dto.request.MenuRateRequest;
import com.ssafy.ibalance.diary.dto.response.*;
import com.ssafy.ibalance.diary.exception.DiaryNotWrittenException;
import com.ssafy.ibalance.diet.dto.DietTotalInfoDto;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.entity.DietMaterial;
import com.ssafy.ibalance.diet.entity.DietMenu;
import com.ssafy.ibalance.diet.exception.MenuInfoNotMatchException;
import com.ssafy.ibalance.diet.repository.diet.DietRepository;
import com.ssafy.ibalance.diet.type.MealTime;
import com.ssafy.ibalance.member.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DietRepository dietRepository;
    private final FastAPIConnectionUtil fastAPIConnectionUtil;
    private final DtoConverter dtoConverter;

    public List<CalendarResponse> getCalendarList(Integer childId, int year, int month, Member member) {
        return dietRepository.getCalendarList(childId, year, month, member);
    }

    public List<DietByDateResponse> getDietByDate(Integer childId, LocalDate date, Member member) {
        return dietRepository.getDietByDate(childId, date, member);
    }

    public DiaryInfoResponse getDiaryWriteInfo(Member member, Long dietId) {
        DietTotalInfoDto dietTotalInfo = dietRepository.getDietTotalInfo(dietId);

        Diet diet = dietTotalInfo.getDiet();
        checkAccessGranted(member, diet.getChild().getId(), diet);

        List<DiaryMenuResponse> diaryMenuList = getDiaryMenuListFromFastAPI(dietTotalInfo.getDietMenuList());
        List<DietMaterialResponse> dietMaterialList
                = DietMaterialResponse.convertToResponse(dietTotalInfo.getDietMaterialList());

        return DiaryInfoResponse.builder()
                .date(diet.getDietDate())
                .menu(diaryMenuList)
                .materials(dietMaterialList)
                .build();
    }

    @Transactional
    public DiarySaveResponse saveDiaryInfo(Member member, Integer childId, DiarySaveRequest request) {
        DietTotalInfoDto dietTotalInfo = dietRepository.getDietTotalInfo(request.getDietId());

        Diet diet = dietTotalInfo.getDiet();
        Child child = diet.getChild();

        if(!child.getId().equals(childId) || !child.getMember().equals(member)) {
            throw new ChildAccessDeniedException("해당 식단의 일기를 작성할 권한이 없습니다.");
        }

        if(request.getMealTime() == null) {
            request.setMealTime("NONE");
        }

        diet.setDiary(request.getContent());
        diet.setReviewed(true);
        diet.setMealTime(MealTime.valueOf(request.getMealTime()));


        saveMenuScore(dietTotalInfo.getDietMenuList(), request.getMenuRate());
        savePickyResult(dietTotalInfo.getDietMaterialList(), request.getPickyIdList());

        return getDiarySaveResponse(dietTotalInfo, diet);
    }


    private void saveMenuScore(List<DietMenu> dietMenuList, List<MenuRateRequest> menuRateRequests) {
        Map<String, List<MenuRateRequest>> menuRateMap =
                menuRateRequests.stream().collect(Collectors.groupingBy(MenuRateRequest::getMenuId));

        dietMenuList.forEach(menu -> {
            if(menuRateMap.containsKey(menu.getMenuId())) {
                MenuRateRequest menuRate = menuRateMap.get(menu.getMenuId()).getFirst();
                menu.setScore(menuRate.getRate().floatValue());
            } else {
                throw new MenuInfoNotMatchException("식단과 메뉴 정보가 일치하지 않습니다.");
            }
        });
    }

    private void savePickyResult(List<DietMaterial> dietMaterialList, List<Long> pickyIdList) {
        Set<Long> pickySet = new HashSet<>(pickyIdList);

        dietMaterialList.stream()
                .filter(material -> pickySet.contains(material.getId()))
                .forEach(material -> material.setPicky(true));
    }

    private static DiarySaveResponse getDiarySaveResponse(DietTotalInfoDto dietTotalInfo, Diet diet) {
        List<DietMaterialResponse> dietMaterialList = dietTotalInfo.getDietMaterialList()
                .stream()
                .map(DietMaterialResponse::convertToResponse)
                .toList();

        return DiarySaveResponse.builder()
                .dietId(diet.getId())
                .date(diet.getDietDate())
                .content(diet.getDiary())
                .mealTime(diet.getMealTime().toString())
                .materials(dietMaterialList)
                .build();
    }
}
