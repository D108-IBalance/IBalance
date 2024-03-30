package com.ssafy.ibalance.diary;

import com.ssafy.ibalance.diary.dto.request.DiarySaveRequest;
import com.ssafy.ibalance.diary.dto.request.MenuRateRequest;
import com.ssafy.ibalance.diet.entity.DietMaterial;
import com.ssafy.ibalance.diet.entity.DietMenu;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiarySteps {

    public static final String content = "동해물과 백두산이 마르고 닳도록, 하느님이 보우하사 우리나라 만세!";

    public DiarySaveRequest 식단_일기_저장(Long targetDietId, List<DietMenu> dietMenuList,
                                     List<DietMaterial> materials, List<Long> indexList, String mealTIme) {

        return getDiarySaveRequest(targetDietId, dietMenuList, materials, indexList, content, mealTIme);
    }

    public DiarySaveRequest 식단_일기_일기누락(Long targetDietId, List<DietMenu> dietMenuList,
                                       List<DietMaterial> materials, List<Long> indexList) {
        return getDiarySaveRequest(targetDietId, dietMenuList, materials, indexList, "", "DINNER");
    }

    private DiarySaveRequest getDiarySaveRequest(Long targetDietId, List<DietMenu> dietMenuList,
                                                 List<DietMaterial> materials, List<Long> indexList, String content, String mealTime) {
        List<MenuRateRequest> menuRate = dietMenuList.stream()
                .filter(menu -> menu.getDiet().getId().equals(targetDietId))
                .map(menu -> getMenuRate(3D, menu.getMenuId()))
                .toList();

        List<Long> pickyIdList = materials.stream()
                .map(DietMaterial::getId)
                .filter(indexList::contains)
                .toList();

        return DiarySaveRequest.builder()
                .dietId(targetDietId)
                .content(content)
                .menuRate(menuRate)
                .mealTime(mealTime)
                .pickyIdList(pickyIdList)
                .build();
    }

    private MenuRateRequest getMenuRate(Double rate, String menuId) {
        return MenuRateRequest.builder()
                .rate(rate)
                .menuId(menuId)
                .build();
    }
}
