package com.ssafy.ibalance.diet.service;

import com.ssafy.ibalance.child.entity.Allergy;
import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.child.entity.ChildAllergy;
import com.ssafy.ibalance.child.entity.RedisChildAllergy;
import com.ssafy.ibalance.child.exception.ChildNotFoundException;
import com.ssafy.ibalance.child.repository.AllergyRepository;
import com.ssafy.ibalance.child.repository.ChildAllergyRepository;
import com.ssafy.ibalance.child.repository.ChildRepository;
import com.ssafy.ibalance.child.repository.RedisChildAllergyRepository;
import com.ssafy.ibalance.common.util.FastAPIConnectionUtil;
import com.ssafy.ibalance.diet.dto.RecommendNeedDto;
import com.ssafy.ibalance.diet.dto.request.RecommendRequest;
import com.ssafy.ibalance.diet.dto.response.*;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.entity.DietMaterial;
import com.ssafy.ibalance.diet.entity.DietMenu;
import com.ssafy.ibalance.diet.entity.RedisRecommendDiet;
import com.ssafy.ibalance.diet.exception.CannotAddDietException;
import com.ssafy.ibalance.diet.exception.RedisWrongDataException;
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
    private final AllergyRepository allergyRepository;
    private final ChildRepository childRepository;
    private final DietMenuRepository dietMenuRepository;
    private final DietMaterialRepository dietMaterialRepository;
    private final RedisChildAllergyRepository redisChildAllergyRepository;
    private final RedisInitDietRepository redisInitDietRepository;
    private final FastAPIConnectionUtil fastAPIConnectionUtil;

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
            childAllergyList.forEach(childAllergy -> allergyList.add(childAllergy.getAllergy().getId()));
        }
        return allergyList;
    }

    public List<String> getPastMenu(Integer childId) {
        return childRepository.getMenuIdByChildIdAndDate(childId, LocalDate.now());
    }

    public List<InitDietResponse> getInitDiet(Integer childId, List<Integer> allergyList, List<String> pastMenu) {
        List<InitDietResponse> initDietResponseList = getInitRecommend(childId, allergyList, pastMenu);

        List<RedisRecommendDiet> redisRecommendDietList = new ArrayList<>();
        for(int day = 0; day < 7; day++) {
            List<String> menuList = new ArrayList<>();
            initDietResponseList.get(day).getMenuList().forEach(menu -> {
                menuList.add(menu.getMenuId());
                pastMenu.add(menu.getMenuId());
            });
            List<List<String>> diet = new ArrayList<>();
            diet.add(menuList);
            redisRecommendDietList.add(RedisRecommendDiet.builder()
                    .id(childId + "_" + day)
                    .dietList(diet).build());
        }
        redisInitDietRepository.saveAll(redisRecommendDietList);

        return initDietResponseList;
    }

    public List<DietMenuResponse> addTempDiet(Integer childId, int dietDay, String allergy, String doNotRecommend) {
        RedisRecommendDiet dayDiet = redisInitDietRepository.findById(childId + "_" + dietDay).orElseThrow(() -> new RedisWrongDataException("Redis에 해당 날짜의 식단 데이터가 없습니다."));
        List<List<String>> dietList = dayDiet.getDietList();
        if(dietList.size() >= 3) {
            throw new CannotAddDietException("일일 추천 가능 식단 개수를 초과했습니다.");
        }
        List<Integer> allergyList = ConvertCookieStringToIntegerList(allergy);
        List<String> doNotRecommendList = ConvertCookieStringToStringList(doNotRecommend);

        List<DietMenuResponse> tempDiet = getTempRecommend(childId, dietDay, allergyList, doNotRecommendList);
        List<String> menuList = tempDiet.stream().map(DietMenuResponse::getMenuId).toList();

        dietList.add(menuList);
        redisInitDietRepository.save(RedisRecommendDiet.builder()
                .id(childId + "_" + dietDay)
                .dietList(dietList)
                .build());

        return tempDiet;
    }

    public List<String> deleteTempDiet(Integer childId, int dietDay, int sequence) {
        RedisRecommendDiet dayDiet = redisInitDietRepository.findById(childId + "_" + dietDay).orElseThrow(() -> new RedisWrongDataException("Redis에 해당 날짜의 식단 데이터가 없습니다."));
        List<String> menuList;
        try {
            menuList = dayDiet.getDietList().get(sequence);
            dayDiet.getDietList().remove(sequence);
        } catch (IndexOutOfBoundsException e) {
            throw new RedisWrongDataException("Redis에 해당 날짜, 해당 순서의 식단 데이터가 없습니다.");
        }
        redisInitDietRepository.save(dayDiet);
        return menuList;
    }

    public MenuDetailResponse changeMenuOfTempDiet(Integer childId, int dietDay, int sequence, String menuId, String allergy, String doNotRecommend) {
        RedisRecommendDiet dayDiet = redisInitDietRepository.findById(childId + "_" + dietDay).orElseThrow(() -> new RedisWrongDataException("Redis에 해당 날짜의 식단 데이터가 없습니다."));
        List<String> dietList = dayDiet.getDietList().get(sequence);
        try {
            for (int menuNum = 0; menuNum < 4; menuNum++) {
                if (dietList.get(menuNum).equals(menuId)) {
                    dietList.remove(menuNum);
                    break;
                }
            }
        } catch (IndexOutOfBoundsException e){
            throw new RedisWrongDataException("해당 식단의 메뉴가 4개가 아닙니다.");
        }

        if(dietList.size() != 3) {
            throw new RedisWrongDataException("해당 메뉴는 존재하지 않습니다.");
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
            RedisRecommendDiet diet = redisInitDietRepository.findById(childId + "_" + day).orElseThrow(() -> new RedisWrongDataException("Redis에 해당 날짜의 식단 데이터가 없습니다."));
            redisRecommendDietList.add(diet);
            diet.getDietList().forEach(menuIdList::addAll);
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

                recommendDiet.getDietList().get(sequence).forEach(id -> {
                    // DietMenu DB 추가
                    dietMenuList.add(DietMenu.builder()
                            .menuId(id)
                            .diet(diet)
                            .score(null)
                            .build());

                    // DietMaterial DB 추가
                    Set<String> materials = new HashSet<>();
                    menuDetailResponseList.stream()
                            .filter(menuDetail -> menuDetail.getMenuId().equals(id))
                            .forEach(menuDetail -> materials.addAll(menuDetail.getMaterials()));

                    materials.forEach(material -> dietMaterialList.add(DietMaterial.builder()
                            .material(material)
                            .diet(diet)
                            .build()));
                });
            }
        }
        // repository로 db 세종류 저장
        List<Diet> diets = dietRepository.saveAll(dietList);
        dietMenuRepository.saveAll(dietMenuList);
        dietMaterialRepository.saveAll(dietMaterialList);

        // redis 데이터 삭제
        redisInitDietRepository.deleteAll(redisRecommendDietList);

        List<Long> dietIds = new ArrayList<>();
        diets.forEach(diet -> dietIds.add(diet.getId()));
        return dietIds;
    }

    private List<MenuDetailResponse> getMenuDetailByMenuId(List<String> menuIdList) {
        // TODO : MongoDB에서 menuId로 메뉴 데이터 가져오기
        List<MenuDetailResponse> menuDetailResponseList = new ArrayList<>();
        menuIdList.forEach(s -> {
            List<String> materials = new ArrayList<>();
            materials.add("재료 " + s);
            List<String> recipes = new ArrayList<>();
            recipes.add("레시피 " + s);
            menuDetailResponseList.add(MenuDetailResponse.builder()
                    .menuId(s)
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
        });
        return menuDetailResponseList;
    }

    private List<InitDietResponse> getInitRecommend(Integer childId, List<Integer> allergyList, List<String> pastMenu) {
        // TODO : childId로 필요 영양소 가져오기
        List<String> allergyName = allergyRepository.findAllergyNameByIdIn(allergyList).stream().map(Allergy::getAllergyName).toList();
        RecommendRequest recommendRequest = RecommendRequest.builder()
                .childId(childId)
                .allergyList(allergyName)
                .cacheList(pastMenu)
                .need(RecommendNeedDto.builder()
                        .calories(250)
                        .carbohydrate(45.2)
                        .protein(21.3)
                        .cellulose(12.1)
                        .build())
                .needType(null)
                .currentMenuIdOfDiet(null)
                .build();

        List<List<LinkedHashMap<String, Object>>> recommendResult = fastAPIConnectionUtil.postApiConnectionResult("/init", recommendRequest, new ArrayList<>());
        return convertInitRecommend(recommendResult);
    }

    private List<InitDietResponse> convertInitRecommend(List<List<LinkedHashMap<String, Object>>> recommendResult) {
        List<List<RecommendResponse>> recommendList = recommendResult.stream().map(
                diet -> diet.stream().map(this::ConvertPythonAPIToResponse).toList()).toList();

        List<InitDietResponse> initDietResponseList = new ArrayList<>();
        for(int day = 0; day < 7; day++) {
            List<RecommendResponse> recommend = recommendList.get(day);
            List<DietMenuResponse> menuList = recommend.stream().map(
                    menu -> DietMenuResponse.builder()
                            .menuId(menu.getMenuId())
                            .menuName(menu.getMenuName())
                            .menuType(MenuType.find(menu.getMenuType()))
                            .build()
            ).toList();

            initDietResponseList.add(InitDietResponse.builder()
                    .dietDay(day)
                    .menuList(menuList)
                    .build());
        }

        return initDietResponseList;
    }

    private List<DietMenuResponse> getTempRecommend(Integer childId, int dietDay, List<Integer> allergyList, List<String> doNotRecommend) {
        // TODO : childId로 필요 영양소 가져오기
        List<String> allergyName = allergyRepository.findAllergyNameByIdIn(allergyList).stream().map(Allergy::getAllergyName).toList();
        RecommendRequest recommendRequest = RecommendRequest.builder()
                .childId(childId)
                .allergyList(allergyName)
                .cacheList(doNotRecommend)
                .need(RecommendNeedDto.builder()
                        .calories(250)
                        .carbohydrate(45.2)
                        .protein(21.3)
                        .cellulose(12.1)
                        .build())
                .needType(null)
                .currentMenuIdOfDiet(null)
                .build();

        // 임시 데이터
        List<DietMenuResponse> menuList = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            menuList.add(DietMenuResponse.builder()
                    .menuId("메뉴" + (i+5) + "번")
                    .menuName("메뉴 " + (i+5) + "번")
                    .menuType(MenuType.RICE)
                    .build());
        }
        return menuList;

//        List<LinkedHashMap<String, Object>> recommendResult = fastAPIConnectionUtil.postApiConnectionResult("", recommendRequest, new ArrayList<>());
//        return convertTempRecommend(recommendResult);
    }

    private List<DietMenuResponse> convertTempRecommend(List<LinkedHashMap<String, Object>> recommendResult) {
        List<RecommendResponse> recommendList = recommendResult.stream()
                .map(this::ConvertPythonAPIToResponse).toList();


        return recommendList.stream().map(
                menu -> DietMenuResponse.builder()
                        .menuId(menu.getMenuId())
                        .menuName(menu.getMenuName())
                        .menuType(MenuType.find(menu.getMenuType()))
                        .build()
                ).toList();
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
            Arrays.stream(cookieSplit)
                    .forEach(id -> result.add(Integer.parseInt(id)));
        } catch (NumberFormatException e) {
            throw new WrongCookieDataException("쿠키에 잘못된 값이 입력되었습니다.");
        }
        return result;
    }

    private List<String> ConvertCookieStringToStringList(String cookie) {
        List<String> result;
        String[] cookieSplit = cookie.split("\\|");
        try{
            result = new ArrayList<>(Arrays.asList(cookieSplit));
        } catch (Exception e) {
            throw new WrongCookieDataException("쿠키에 잘못된 값이 입력되었습니다.");
        }
        return result;
    }

    private RecommendResponse ConvertPythonAPIToResponse(LinkedHashMap<String, Object> recommendResult) {
        return RecommendResponse.builder()
                .menuId((String) recommendResult.get("menu_id"))
                .menuName((String) recommendResult.get("menu_name"))
                .menuImgUrl((String) recommendResult.get("img_url"))
                .menuType((String) recommendResult.get("type"))
                .calorie(Integer.parseInt((String) recommendResult.get("cal")))
                .carbohydrate(Double.parseDouble((String) recommendResult.get("carbohydrate")))
                .protein(Double.parseDouble((String) recommendResult.get("protein")))
                .fat(Double.parseDouble((String) recommendResult.get("fat")))
                .materials((List<String>) recommendResult.get("material"))
                .recipeList((List<String>) ((Map<String, Object>) recommendResult.get("recipe")).get("content_list"))
                .need((String) ((Map<String, Object>) recommendResult.get("recipe")).get("need"))
                .build();
    }
}
