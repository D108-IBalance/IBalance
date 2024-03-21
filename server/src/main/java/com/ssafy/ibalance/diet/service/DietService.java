package com.ssafy.ibalance.diet.service;

import com.ssafy.ibalance.child.entity.ChildAllergy;
import com.ssafy.ibalance.child.entity.RedisChildAllergy;
import com.ssafy.ibalance.child.repository.ChildAllergyRepository;
import com.ssafy.ibalance.child.repository.ChildRepository;
import com.ssafy.ibalance.child.repository.RedisChildAllergyRepository;
import com.ssafy.ibalance.diet.dto.MenuDetailDto;
import com.ssafy.ibalance.diet.dto.MenuDto;
import com.ssafy.ibalance.diet.dto.response.InitDietResponse;
import com.ssafy.ibalance.diet.dto.response.RecommendedDietResponse;
import com.ssafy.ibalance.diet.repository.DietRepository;
import com.ssafy.ibalance.diet.type.MenuType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DietService {

    private final DietRepository dietRepository;
    private final ChildAllergyRepository childAllergyRepository;
    private final ChildRepository childRepository;
    private final RedisChildAllergyRepository redisChildAllergyRepository;

    public List<RecommendedDietResponse> getRecommendedDiet(Integer childId, LocalDate today) {
        LocalDate endday = today.plusDays(6);
        return dietRepository.getDietMenuByDate(childId, today, endday);
    }

    public List<MenuDetailDto> getDietDetail(Long dietId) {
        List<Integer> menuIdList = dietRepository.getMenuIdByDietId(dietId);
        return getMenuDetailByMenuId(menuIdList);
    }

    public List<Integer> getAllergy(Integer childId) {
        Optional<RedisChildAllergy> redisChildAllergy = redisChildAllergyRepository.findById(childId);
        List<Integer> allergyList = new ArrayList<>();
        if(redisChildAllergy.isPresent()) {
            List<ChildAllergy> childAllergyList = childAllergyRepository.findByIdIn(redisChildAllergy.get().getChildAllergyId());
            for(ChildAllergy childAllergy : childAllergyList) {
                allergyList.add(childAllergy.getAllergy().getId());
            }
        }
        return allergyList;
    }

    public List<Integer> getPastMenu(Integer childId) {
        return childRepository.getMenuIdByChildIdAndDate(childId, LocalDate.now());
    }

    public List<InitDietResponse> getInitDiet(Integer childId, List<Integer> allergyList, List<Integer> pastMenu) {
        return getInitRecommendByChildId(childId, allergyList, pastMenu);
    }


    private List<MenuDetailDto> getMenuDetailByMenuId(List<Integer> menuIdList) {
        // TODO : MongoDB에서 menuId로 메뉴 데이터 가져오기
        List<MenuDetailDto> menuDetailDtoList = new ArrayList<>();
        for(Integer i : menuIdList) {
            menuDetailDtoList.add(MenuDetailDto.builder()
                    .menuId(i)
                    .menuName("메뉴 이름")
                    .menuImgUrl("메뉴 이미지")
                    .menuType(MenuType.RICE)
                    .calorie(123)
                    .carbohydrate(1.23)
                    .protein(4.56)
                    .fat(7.89)
                    .materials(null)
                    .recipe(null)
                    .build());
        }
        return menuDetailDtoList;
    }

    private List<InitDietResponse> getInitRecommendByChildId(Integer childId, List<Integer> allergyList, List<Integer>pastMenu) {
        // TODO : FastAPI에서 자녀 정보를 기준으로 일주일치 초기 추천 식단 받기
        List<InitDietResponse> initDietResponseList = new ArrayList<>();
        for(int i = 1; i <= 7; i++) {
            List<MenuDto> menuList = new ArrayList<>();
            for(int j = 0; j < 4; j++) {
                menuList.add(MenuDto.builder()
                        .menuId(j+1)
                        .menuName("메뉴 " + (j+1) + "번")
                        .menuType(MenuType.RICE)
                        .build());
            }
            initDietResponseList.add(InitDietResponse.builder().dietDay(i).menuList(menuList).build());
        }
        return initDietResponseList;
    }
}
