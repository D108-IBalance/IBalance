package com.ssafy.ibalance.diet.service;

import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.child.entity.ChildAllergy;
import com.ssafy.ibalance.child.entity.RedisChildAllergy;
import com.ssafy.ibalance.child.exception.ChildNotFoundException;
import com.ssafy.ibalance.child.repository.ChildAllergyRepository;
import com.ssafy.ibalance.child.repository.ChildRepository;
import com.ssafy.ibalance.child.repository.RedisChildAllergyRepository;
import com.ssafy.ibalance.diet.dto.response.MenuDetailResponse;
import com.ssafy.ibalance.diet.dto.RedisDietDto;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import com.ssafy.ibalance.diet.dto.response.DietMenuResponse;
import com.ssafy.ibalance.diet.dto.response.InitDietResponse;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.entity.DietMaterial;
import com.ssafy.ibalance.diet.entity.DietMenu;
import com.ssafy.ibalance.diet.entity.RedisRecommendDiet;
import com.ssafy.ibalance.diet.exception.CannotAddDietException;
import com.ssafy.ibalance.diet.exception.RedisException;
import com.ssafy.ibalance.diet.exception.WrongCookieDataException;
import com.ssafy.ibalance.diet.repository.DietMaterialRepository;
import com.ssafy.ibalance.diet.repository.DietMenuRepository;
import com.ssafy.ibalance.diet.repository.DietRepository;
import com.ssafy.ibalance.diet.repository.RedisInitDietRepository;
import com.ssafy.ibalance.diet.type.MenuType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class DietService {

    private final DietRepository dietRepository;
    private final ChildAllergyRepository childAllergyRepository;
    private final ChildRepository childRepository;
    private final DietMenuRepository dietMenuRepository;
    private final DietMaterialRepository dietMaterialRepository;
    private final RedisChildAllergyRepository redisChildAllergyRepository;
    private final RedisInitDietRepository redisInitDietRepository;

    public List<DietByDateResponse> getRecommendedDiet(Integer childId, LocalDate today) {
        LocalDate endday = today.plusDays(6);
        return dietRepository.getDietByDateBetween(childId, today, endday);
    }

    public List<MenuDetailResponse> getDietDetail(Long dietId) {
        List<String> menuIdList = dietRepository.getMenuIdByDietId(dietId);
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

    public List<String> getPastMenu(Integer childId) {
        return childRepository.getMenuIdByChildIdAndDate(childId, LocalDate.now());
    }

    public List<InitDietResponse> getInitDiet(Integer childId, List<Integer> allergyList, List<String> pastMenu) {
        List<InitDietResponse> initDietResponseList = getInitRecommend(childId, allergyList, pastMenu);

        for(int day = 0; day < 7; day++) {
            List<String> menuList = new ArrayList<>();
            for(DietMenuResponse menu : initDietResponseList.get(day).getMenuList()) {
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
        List<Integer> allergyList = ConvertCookieStringToIntegerList(allergy);
        List<String> doNotRecommendList = ConvertCookieStringToStringList(doNotRecommend);

        List<DietMenuResponse> tempDiet = getTempRecommend(childId, dietDay, allergyList, doNotRecommendList);

        List<String> menuList = new ArrayList<>();
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

    public List<String> deleteTempDiet(Integer childId, int dietDay, int sequence) {
        RedisRecommendDiet dayDiet = redisInitDietRepository.findById(childId + "_" + dietDay).orElseThrow(() -> new RedisException("Redis에 해당 날짜의 식단 데이터가 없습니다."));
        List<String> menuList;
        try {
            menuList = dayDiet.getDietList().get(sequence).getMenuList();
            dayDiet.getDietList().remove(sequence);
        } catch (Exception e) {
            throw new RedisException("Redis에 해당 날짜, 해당 순서의 식단 데이터가 없습니다.");
        }
        redisInitDietRepository.save(dayDiet);
        return menuList;
    }

    public MenuDetailResponse changeMenuOfTempDiet(Integer childId, int dietDay, int sequence, String menuId, String allergy, String doNotRecommend) {
        RedisRecommendDiet dayDiet = redisInitDietRepository.findById(childId + "_" + dietDay).orElseThrow(() -> new RedisException("Redis에 해당 날짜의 식단 데이터가 없습니다."));
        List<String> dietList = dayDiet.getDietList().get(sequence).getMenuList();
        for(int menuNum = 0; menuNum < 4; menuNum++) {
            if(dietList.get(menuNum).equals(menuId)) {
                dietList.remove(menuNum);
                break;
            }
        }
        if(dietList.size() != 3) {
            throw new RedisException("해당 메뉴는 존재하지 않습니다.");
        }

        List<Integer> allergyList = ConvertCookieStringToIntegerList(allergy);
        List<String> doNotRecommendList = ConvertCookieStringToStringList(doNotRecommend);

        MenuDetailResponse menu = getMenuRecommend(childId, allergyList, doNotRecommendList, dietList);

        dietList.add(menu.getMenuId());
        redisInitDietRepository.save(dayDiet);

        return menu;
    }

    public List<Long> insertTempDiet(Integer childId, LocalDate startDate) {
        List<RedisRecommendDiet> redisRecommendDietList = new ArrayList<>();
        List<String> menuIdList = new ArrayList<>();
        for(int day = 0; day < 7; day++) {
            RedisRecommendDiet diet = redisInitDietRepository.findById(childId + "_" + day).orElseThrow(() -> new RedisException("Redis에 해당 날짜의 식단 데이터가 없습니다."));
            redisRecommendDietList.add(diet);
            for(RedisDietDto menus : diet.getDietList()) {
                menuIdList.addAll(menus.getMenuList());
            }
            redisInitDietRepository.delete(diet);
        }

        List<MenuDetailResponse> menuDetailResponseList = getMenuDetailByMenuId(menuIdList);
        Child child = childRepository.findById(childId).orElseThrow(() -> new ChildNotFoundException("해당 자녀를 찾을 수 없습니다."));

        List<Diet> dietList = new ArrayList<>();
        List<DietMaterial> dietMaterialList = new ArrayList<>();
        List<DietMenu> dietMenuList = new ArrayList<>();
        for(int day = 0; day < 7; day++) {
            RedisRecommendDiet recommendDiet = redisRecommendDietList.get(day);
            for(int sequence = 0; sequence < recommendDiet.getDietList().size(); sequence++) {
                // Diet DB 추가
                Diet diet = Diet.builder()
                        .dietDate(startDate.plusDays(day))
                        .sequence(1)
                        .diary(null)
                        .child(child)
                        .build();
                dietList.add(diet);

                for(String id : recommendDiet.getDietList().get(sequence).getMenuList()) {
                    // DietMenu DB 추가
                    dietMenuList.add(DietMenu.builder()
                            .menuId(id)
                            .diet(diet)
                            .score(null)
                            .build());

                    // DietMaterial DB 추가
                    Set<String> materials = new HashSet<>();
                    for(MenuDetailResponse menuDetail : menuDetailResponseList) {
                        if(menuDetail.getMenuId().equals(id)) {
                            materials.addAll(menuDetail.getMaterials());
                        }
                    }
                    for(String material : materials) {
                        dietMaterialList.add(DietMaterial.builder()
                                .material(material)
                                .diet(diet)
                                .build());
                    }
                }
            }
        }
        // repository로 db 세종류 저장
        List<Diet> diets = dietRepository.saveAll(dietList);
        dietMenuRepository.saveAll(dietMenuList);
        dietMaterialRepository.saveAll(dietMaterialList);

        List<Long> dietIds = new ArrayList<>();
        for(Diet diet : diets) {
            dietIds.add(diet.getId());
        }
        return dietIds;
    }

    private List<MenuDetailResponse> getMenuDetailByMenuId(List<String> menuIdList) {
        // TODO : MongoDB에서 menuId로 메뉴 데이터 가져오기
        List<MenuDetailResponse> menuDetailResponseList = new ArrayList<>();
        for(String s : menuIdList) {
            List<String> materials = new ArrayList<>();
            materials.add("재료 " + s);
            List<String> recipes = new ArrayList<>();
            recipes.add("레시피 " + s);
            menuDetailResponseList.add(MenuDetailResponse.builder()
                    .menuId("메뉴" + s)
                    .menuName("메뉴 이름")
                    .menuImgUrl("메뉴 이미지")
                    .menuType(MenuType.RICE)
                    .calorie(123)
                    .carbohydrate(1.23)
                    .protein(4.56)
                    .fat(7.89)
                    .materials(materials)
                    .recipe(recipes)
                    .build());
        }
        return menuDetailResponseList;
    }

    private List<InitDietResponse> getInitRecommend(Integer childId, List<Integer> allergyList, List<String> pastMenu) {
        // TODO : FastAPI에서 자녀 정보를 기준으로 일주일치 초기 추천 식단 받기
        List<InitDietResponse> initDietResponseList = new ArrayList<>();
        for(int i = 1; i <= 7; i++) {
            List<DietMenuResponse> menuList = new ArrayList<>();
            for(int j = 0; j < 4; j++) {
                menuList.add(DietMenuResponse.builder()
                        .menuId("메뉴" + (j+1) + "번")
                        .menuName("메뉴 " + (j+1) + "번")
                        .menuType(MenuType.RICE)
                        .build());
            }
            initDietResponseList.add(InitDietResponse.builder().dietDay(i).menuList(menuList).build());
        }
        return initDietResponseList;
    }

    private List<DietMenuResponse> getTempRecommend(Integer childId, int dietDay, List<Integer> allergyList, List<String> doNotRecommend) {
        // TODO : FastAPI에서 식단 하나 추천 받기
        List<DietMenuResponse> menuList = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            menuList.add(DietMenuResponse.builder()
                    .menuId("메뉴" + (i+5) + "번")
                    .menuName("메뉴 " + (i+5) + "번")
                    .menuType(MenuType.RICE)
                    .build());
        }
        return menuList;
    }

    private MenuDetailResponse getMenuRecommend(Integer childId, List<Integer> allergyList, List<String> doNotRecommend, List<String> menuList) {
        // TODO : FastAPI에서 자녀 정보와 해당 식단의 다른 메뉴들을 기준으로 해당 메뉴와 비슷한 영양성분의 추천 메뉴 받기
        List<String> materials = new ArrayList<>();
        materials.add("재료1");
        List<String> recipes = new ArrayList<>();
        recipes.add("레시피1");
        return MenuDetailResponse.builder()
                .menuId("메뉴9번")
                .menuName("메뉴 9번")
                .menuImgUrl("이미지 url")
                .menuType(MenuType.SIDE)
                .calorie(543)
                .carbohydrate(543.21)
                .protein(543.21)
                .fat(543.21)
                .materials(materials)
                .recipe(recipes)
                .build();
    }

    private List<Integer> ConvertCookieStringToIntegerList(String cookie) {
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

    private List<String> ConvertCookieStringToStringList(String cookie) {
        List<String> result = new ArrayList<>();
        String[] cookieSplit = cookie.split("\\|");
        try{
            result.addAll(Arrays.asList(cookieSplit));
        } catch (Exception e) {
            throw new WrongCookieDataException("쿠키에 잘못된 값이 입력되었습니다.");
        }
        return result;
    }
}
