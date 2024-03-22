package com.ssafy.ibalance.diet.service;

import com.ssafy.ibalance.child.entity.ChildAllergy;
import com.ssafy.ibalance.child.entity.RedisChildAllergy;
import com.ssafy.ibalance.child.repository.ChildAllergyRepository;
import com.ssafy.ibalance.child.repository.ChildRepository;
import com.ssafy.ibalance.child.repository.RedisChildAllergyRepository;
import com.ssafy.ibalance.diet.dto.response.MenuDetailResponse;
import com.ssafy.ibalance.diet.dto.RedisDietDto;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import com.ssafy.ibalance.diet.dto.response.DietMenuResponse;
import com.ssafy.ibalance.diet.dto.response.InitDietResponse;
import com.ssafy.ibalance.diet.entity.RedisRecommendDiet;
import com.ssafy.ibalance.diet.exception.CannotAddDietException;
import com.ssafy.ibalance.diet.exception.RedisException;
import com.ssafy.ibalance.diet.exception.WrongCookieDataException;
import com.ssafy.ibalance.diet.repository.DietRepository;
import com.ssafy.ibalance.diet.repository.RedisInitDietRepository;
import com.ssafy.ibalance.diet.type.MenuType;
import com.ssafy.ibalance.material.dto.MaterialDto;
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
    private final RedisInitDietRepository redisInitDietRepository;

    public List<DietByDateResponse> getRecommendedDiet(Integer childId, LocalDate today) {
        LocalDate endday = today.plusDays(6);
        return dietRepository.getDietByDateBetween(childId, today, endday);
    }

    public List<MenuDetailResponse> getDietDetail(Long dietId) {
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
        List<InitDietResponse> initDietResponseList = getInitRecommend(childId, allergyList, pastMenu);

        for(int day = 1; day <= 7; day++) {
            List<Integer> menuList = new ArrayList<>();
            for(DietMenuResponse menu : initDietResponseList.get(day-1).getMenuList()) {
                menuList.add(menu.getMenuId());
                pastMenu.add(menu.getMenuId());
            }
            List<RedisDietDto> diet = new ArrayList<>();
            diet.add(RedisDietDto.builder().menuList(menuList).build());
            redisInitDietRepository.save(RedisRecommendDiet.builder()
                    .id(childId + "_" + day)
                    .dietList(diet).build());
        }

        return initDietResponseList;
    }

    public List<DietMenuResponse> addTempDiet(Integer childId, int dietDay, String allergy, String doNotRecommend) {
        RedisRecommendDiet dayDiet = redisInitDietRepository.findById(childId + "_" + dietDay).orElseThrow(() -> new RedisException("Redis에 해당 날짜의 식단 데이터가 없습니다."));
        List<RedisDietDto> dietList = dayDiet.getDietList();
        if(dietList.size() >= 3) {
            throw new CannotAddDietException("일일 추천 가능 식단 개수를 초과했습니다.");
        }
        List<Integer> allergyList = ConvertCookieStringToList(allergy);
        List<Integer> doNotRecommendList = ConvertCookieStringToList(doNotRecommend);

        List<DietMenuResponse> tempDiet = getTempRecommend(childId, dietDay, allergyList, doNotRecommendList);

        List<Integer> menuList = new ArrayList<>();
        for(DietMenuResponse menu : tempDiet) {
            menuList.add(menu.getMenuId());
        }
        dietList.add(RedisDietDto.builder().menuList(menuList).build());
        redisInitDietRepository.save(RedisRecommendDiet.builder()
                .id(childId + "_" + dietDay)
                .dietList(dietList)
                .build());

        return tempDiet;
    }

    public List<Integer> deleteTempDiet(Integer childId, int dietDay, int sequence) {
        RedisRecommendDiet dayDiet = redisInitDietRepository.findById(childId + "_" + dietDay).orElseThrow(() -> new RedisException("Redis에 해당 날짜의 식단 데이터가 없습니다."));
        List<Integer> menuList = new ArrayList<>();
        try {
            menuList = dayDiet.getDietList().get(sequence-1).getMenuList();
            dayDiet.getDietList().remove(sequence-1);
        } catch (Exception e) {
            throw new RedisException("Redis에 해당 날짜, 해당 순서의 식단 데이터가 없습니다.");
        }
        redisInitDietRepository.save(dayDiet);
        return menuList;
    }

    public MenuDetailResponse changeMenuOfTempDiet(Integer childId, int dietDay, int sequence, Integer menuId, String allergy, String doNotRecommend) {
        RedisRecommendDiet dayDiet = redisInitDietRepository.findById(childId + "_" + dietDay).orElseThrow(() -> new RedisException("Redis에 해당 날짜의 식단 데이터가 없습니다."));
        List<Integer> dietList = dayDiet.getDietList().get(sequence-1).getMenuList();
        for(int menuNum = 0; menuNum < 4; menuNum++) {
            if(dietList.get(menuNum).equals(menuId)) {
                dietList.remove(menuNum);
                break;
            }
        }

        List<Integer> allergyList = ConvertCookieStringToList(allergy);
        List<Integer> doNotRecommendList = ConvertCookieStringToList(doNotRecommend);

        MenuDetailResponse menu = getMenuRecommend(childId, allergyList, doNotRecommendList, dietList);

        dietList.add(menu.getMenuId());
        redisInitDietRepository.save(dayDiet);

        return menu;
    }

    private List<MenuDetailResponse> getMenuDetailByMenuId(List<Integer> menuIdList) {
        // TODO : MongoDB에서 menuId로 메뉴 데이터 가져오기
        List<MenuDetailResponse> menuDetailResponseList = new ArrayList<>();
        for(Integer i : menuIdList) {
            menuDetailResponseList.add(MenuDetailResponse.builder()
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
        return menuDetailResponseList;
    }

    private List<InitDietResponse> getInitRecommend(Integer childId, List<Integer> allergyList, List<Integer> pastMenu) {
        // TODO : FastAPI에서 자녀 정보를 기준으로 일주일치 초기 추천 식단 받기
        List<InitDietResponse> initDietResponseList = new ArrayList<>();
        for(int i = 1; i <= 7; i++) {
            List<DietMenuResponse> menuList = new ArrayList<>();
            for(int j = 0; j < 4; j++) {
                menuList.add(DietMenuResponse.builder()
                        .menuId(j+1)
                        .menuName("메뉴 " + (j+1) + "번")
                        .menuType(MenuType.RICE)
                        .build());
            }
            initDietResponseList.add(InitDietResponse.builder().dietDay(i).menuList(menuList).build());
        }
        return initDietResponseList;
    }

    private List<DietMenuResponse> getTempRecommend(Integer childId, int dietDay, List<Integer> allergyList, List<Integer> doNotRecommend) {
        // TODO : FastAPI에서 식단 하나 추천 받기
        List<DietMenuResponse> menuList = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            menuList.add(DietMenuResponse.builder()
                    .menuId(i+5)
                    .menuName("메뉴 " + (i+5) + "번")
                    .menuType(MenuType.RICE)
                    .build());
        }
        return menuList;
    }

    private MenuDetailResponse getMenuRecommend(Integer childId, List<Integer> allergyList, List<Integer> doNotRecommend, List<Integer> menuList) {
        // TODO : FastAPI에서 자녀 정보와 해당 식단의 다른 메뉴들을 기준으로 해당 메뉴와 비슷한 영양성분의 추천 메뉴 받기
        List<MaterialDto> materials = new ArrayList<>();
        materials.add(MaterialDto.builder().materialId(1).materialName("재료1").build());
        List<String> recipes = new ArrayList<>();
        recipes.add("레시피1");
        return MenuDetailResponse.builder()
                .menuId(9)
                .menuName("메뉴 9번")
                .menuImgUrl("이미지 url")
                .menuType(MenuType.MAIN)
                .calorie(543)
                .carbohydrate(543.21)
                .protein(543.21)
                .fat(543.21)
                .materials(materials)
                .recipe(recipes)
                .build();
    }

    private List<Integer> ConvertCookieStringToList(String cookie) {
        List<Integer> result = new ArrayList<>();
        String[] cookieSplit = cookie.split("\\|");
        try{
            for(String id : cookieSplit) {
                result.add(Integer.parseInt(id));
            }
        } catch (Exception e) {
            throw new WrongCookieDataException("쿠키에 잘못된 값이 입력되었습니다.");
        }
        return result;
    }
}
