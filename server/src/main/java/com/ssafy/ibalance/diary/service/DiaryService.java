package com.ssafy.ibalance.diary.service;

import com.ssafy.ibalance.common.util.DtoConverter;
import com.ssafy.ibalance.common.util.FastAPIConnectionUtil;
import com.ssafy.ibalance.diary.dto.response.CalendarResponse;
import com.ssafy.ibalance.diary.dto.response.DiaryInfoResponse;
import com.ssafy.ibalance.diary.dto.response.DiaryMenuResponse;
import com.ssafy.ibalance.diary.dto.response.DietMaterialResponse;
import com.ssafy.ibalance.diet.dto.DietDetailDto;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import com.ssafy.ibalance.diet.repository.diet.DietRepository;
import com.ssafy.ibalance.diet.repository.dietmenu.DietMenuRepository;
import com.ssafy.ibalance.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DietRepository dietRepository;
    private final DietMenuRepository dietMenuRepository;
    private final FastAPIConnectionUtil fastAPIConnectionUtil;
    private final DtoConverter dtoConverter;

    public List<CalendarResponse> getCalendarList(Integer childId, int year, int month, Member member) {
        return dietRepository.getCalendarList(childId, year, month, member);
    }

    public List<DietByDateResponse> getDietByDate(Integer childId, LocalDate date, Member member) {
        return dietRepository.getDietByDate(childId, date, member);
    }

    public DiaryInfoResponse getDiaryWriteInfo(Member member, Long dietId) {

        DietDetailDto dietDetail = dietMenuRepository.getDietAndMenu(member, dietId);

        ArrayList<LinkedHashMap<String, String>> diaryMenuResponses
                = fastAPIConnectionUtil.postApiConnectionResult("/info", dietDetail.getMenuIdList(), new ArrayList<>());

        List<DiaryMenuResponse> diaryMenuList = diaryMenuResponses.stream()
                .map(r -> dtoConverter.convertFromMap(r, new DiaryMenuResponse()))
                .toList();

        List<DietMaterialResponse> dietMaterialList = dietDetail.getDietMaterialList()
                .stream()
                .map(DietMaterialResponse::convertToResponse)
                .toList();

        return DiaryInfoResponse.builder()
                .date(dietDetail.getDiet().getDietDate())
                .menu(diaryMenuList)
                .materials(dietMaterialList)
                .build();
    }
}
