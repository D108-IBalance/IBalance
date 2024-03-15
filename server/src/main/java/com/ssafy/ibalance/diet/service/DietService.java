package com.ssafy.ibalance.diet.service;

import com.ssafy.ibalance.diet.dto.MenuDetailDto;
import com.ssafy.ibalance.diet.dto.response.ChildDietResponse;
import com.ssafy.ibalance.diet.repository.DietRepository;
import com.ssafy.ibalance.diet.type.MenuType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DietService {

    private final DietRepository dietRepository;

    public List<ChildDietResponse> getChildDiet(Integer childId, LocalDate today) {

        LocalDate endday = today.plusDays(6);
        return dietRepository.getDietMenuByDate(childId, today, endday);
    }

    public List<MenuDetailDto> getDietDetail(Long dietId) {
        List<Integer> menuIdList = dietRepository.getMenuIdByDietId(dietId);
        return getMenuDetailByMenuId(menuIdList);
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

}
