package com.ssafy.ibalance.diet.service;

import com.ssafy.ibalance.child.entity.*;
import com.ssafy.ibalance.child.exception.ChildNotFoundException;
import com.ssafy.ibalance.child.repository.*;
import com.ssafy.ibalance.child.type.WeightCondition;
import com.ssafy.ibalance.common.util.FastAPIConnectionUtil;
import com.ssafy.ibalance.diet.dto.RecommendNeedDto;
import com.ssafy.ibalance.diet.dto.RedisDietDto;
import com.ssafy.ibalance.diet.dto.request.RecommendRequest;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import com.ssafy.ibalance.diet.dto.response.DietMenuResponse;
import com.ssafy.ibalance.diet.dto.response.InitDietResponse;
import com.ssafy.ibalance.diet.dto.response.MenuDetailResponse;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.entity.DietMaterial;
import com.ssafy.ibalance.diet.entity.DietMenu;
import com.ssafy.ibalance.diet.entity.RedisRecommendDiet;
import com.ssafy.ibalance.diet.exception.CannotAddDietException;
import com.ssafy.ibalance.diet.exception.RedisWrongDataException;
import com.ssafy.ibalance.diet.exception.WrongCookieDataException;
import com.ssafy.ibalance.diet.repository.DietMenuRepository;
import com.ssafy.ibalance.diet.repository.RedisInitDietRepository;
import com.ssafy.ibalance.diet.repository.diet.DietRepository;
import com.ssafy.ibalance.diet.repository.dietmaterial.DietMaterialRepository;
import com.ssafy.ibalance.diet.type.DietNutrition;
import com.ssafy.ibalance.diet.type.MealTime;
import com.ssafy.ibalance.diet.type.MenuType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DietService {

    private final DietRepository dietRepository;
    private final ChildAllergyRepository childAllergyRepository;
    private final AllergyRepository allergyRepository;
    private final ChildRepository childRepository;
    private final DietMenuRepository dietMenuRepository;
    private final DietMaterialRepository dietMaterialRepository;
    private final BmiConditionRepository bmiConditionRepository;
    private final RedisChildAllergyRepository redisChildAllergyRepository;
    private final RedisInitDietRepository redisInitDietRepository;
    private final FastAPIConnectionUtil fastAPIConnectionUtil;


    public List<DietByDateResponse> getRecommendedDiet(Integer childId, LocalDate today) {
        LocalDate endday = today.plusDays(6);
        return dietRepository.getDietByDateBetween(childId, today, endday);
    }

    public List<MenuDetailResponse> getDietDetail(Long dietId) {
        List<String> menuIdList = dietRepository.getMenuIdByDietId(dietId);
        return convertPythonInfoAPIToResponse(fastAPIConnectionUtil.postApiConnectionResult("/info", menuIdList, new ArrayList<>()));
    }

    public List<Integer> getAllergy(Integer childId) {
        Optional<RedisChildAllergy> redisChildAllergy = redisChildAllergyRepository.findById(childId);

        List<Integer> allergyList = new ArrayList<>();

        if(redisChildAllergy.isPresent()) {
            List<ChildAllergy> childAllergyList = childAllergyRepository.findByIdIn(redisChildAllergy.get().getChildAllergyId());
            allergyList = childAllergyList.stream().map(childAllergy -> childAllergy.getAllergy().getId()).toList();
        }

        return allergyList;
    }

    public List<String> getPastMenu(Integer childId) {
        return childRepository.getMenuIdByChildIdAndDate(childId, LocalDate.now());
    }

    public List<InitDietResponse> getInitDiet(Integer childId, List<Integer> allergyList, List<String> pastMenu) {
        Child child = childRepository.findById(childId).orElseThrow(() -> new ChildNotFoundException("해당 ID의 자녀를 찾을 수 없습니다."));

        List<InitDietResponse> initDietResponseList = getInitRecommend(child, allergyList, pastMenu);

        List<RedisRecommendDiet> redisRecommendDietList = new ArrayList<>();

        for (int day = 0; day < 7; day++) {
            List<String> menuList = new ArrayList<>();

            initDietResponseList.get(day).getMenuList().getFirst().forEach(menu -> {
                menuList.add(menu.getMenuId());
                pastMenu.add(menu.getMenuId());
            });

            List<RedisDietDto> diet = new ArrayList<>();

            diet.add(RedisDietDto.builder().menuList(menuList).build());

            redisRecommendDietList.add(RedisRecommendDiet.builder()
                    .id(childId + "_" + day)
                    .dietList(diet).build());
        }

        redisInitDietRepository.saveAll(redisRecommendDietList);

        return initDietResponseList;
    }

    public List<MenuDetailResponse> getInitDietDetail(Integer childId, int dietDay, int sequence) {
        RedisRecommendDiet diet = redisInitDietRepository.findById(childId + "_" + dietDay).orElseThrow(() -> new RedisWrongDataException("해당 식단이 존재하지 않습니다."));
        List<String> menuList = diet.getDietList().get(sequence).getMenuList();

        List<LinkedHashMap<String, Object>> menuDetailList = fastAPIConnectionUtil.postApiConnectionResult("/info", menuList, new ArrayList<>());

        return convertPythonInfoAPIToResponse(menuDetailList);
    }

    public List<DietMenuResponse> addTempDiet(Integer childId, int dietDay, String allergy, String doNotRecommend) {
        RedisRecommendDiet dayDiet = redisInitDietRepository.findById(childId + "_" + dietDay).orElseThrow(() -> new RedisWrongDataException("Redis에 해당 날짜의 식단 데이터가 없습니다."));
        List<RedisDietDto> dietList = dayDiet.getDietList();

        if(dietList.size() >= 3) {
            throw new CannotAddDietException("일일 추천 가능 식단 개수를 초과했습니다.");
        }

        List<Integer> allergyList = convertCookieStringToIntegerList(allergy);
        List<String> doNotRecommendList = convertCookieStringToStringList(doNotRecommend);

        List<DietMenuResponse> tempDiet = getTempRecommend(childId, allergyList, doNotRecommendList);
        List<String> menuList = tempDiet.stream().map(DietMenuResponse::getMenuId).toList();

        dietList.add(RedisDietDto.builder().menuList(menuList).build());
        redisInitDietRepository.save(RedisRecommendDiet.builder()
                .id(childId + "_" + dietDay)
                .dietList(dietList)
                .build());

        return tempDiet;
    }

    public List<String> deleteTempDiet(Integer childId, int dietDay, int sequence) {
        RedisRecommendDiet dayDiet = redisInitDietRepository.findById(childId + "_" + dietDay).orElseThrow(() -> new RedisWrongDataException("Redis에 해당 날짜의 식단 데이터가 없습니다."));

        if(dayDiet.getDietList().size() < sequence + 1) {
            throw new RedisWrongDataException("Redis에 해당 날짜, 해당 순서의 식단 데이터가 없습니다.");
        }

        List<String> menuList = dayDiet.getDietList().get(sequence).getMenuList();
        dayDiet.getDietList().remove(sequence);

        redisInitDietRepository.save(dayDiet);
        return menuList;
    }

    public MenuDetailResponse changeMenuOfTempDiet(Integer childId, int dietDay, int sequence, String menuId, String allergy, String doNotRecommend) {
        RedisRecommendDiet dayDiet = redisInitDietRepository.findById(childId + "_" + dietDay).orElseThrow(() -> new RedisWrongDataException("Redis에 해당 날짜의 식단 데이터가 없습니다."));
        List<String> dietList = dayDiet.getDietList().get(sequence).getMenuList();

        if(dietList.size() != 4) {
            throw new RedisWrongDataException("해당 식단의 메뉴가 4개가 아닙니다.");
        }

        for (int menuNum = 0; menuNum < 4; menuNum++) {
            if(dietList.get(menuNum).equals(menuId)) {
                dietList.remove(menuNum);
                break;
            }
        }

        if(dietList.size() != 3) {
            throw new RedisWrongDataException("해당 메뉴는 존재하지 않습니다.");
        }

        List<Integer> allergyList = convertCookieStringToIntegerList(allergy);
        List<String> doNotRecommendList = convertCookieStringToStringList(doNotRecommend);

        MenuDetailResponse menu = getMenuRecommend(childId, allergyList, doNotRecommendList, dietList, menuId);

        dietList.add(menu.getMenuId());
        redisInitDietRepository.save(dayDiet);

        return menu;
    }

    @Transactional
    public List<Long> insertTempDiet(Integer childId, LocalDate startDate) {
        List<RedisRecommendDiet> redisRecommendDietList = new ArrayList<>();
        List<List<String>> menuIdList = new ArrayList<>();

        for (int day = 0; day < 7; day++) {
            List<String> menuInDiet = new ArrayList<>();
            RedisRecommendDiet diet = redisInitDietRepository.findById(childId + "_" + day).orElseThrow(() -> new RedisWrongDataException("Redis에 해당 날짜의 식단 데이터가 없습니다."));
            redisRecommendDietList.add(diet);
            diet.getDietList().forEach(menus -> menuInDiet.addAll(menus.getMenuList()));
            menuIdList.add(menuInDiet);
            redisInitDietRepository.delete(diet);
        }

        List<MenuDetailResponse> menuDetailResponseList  = new ArrayList<>();

        menuIdList.forEach(list -> menuDetailResponseList.addAll(convertPythonInfoAPIToResponse(fastAPIConnectionUtil.postApiConnectionResult("/info", list, new ArrayList<>()))));
        Child child = childRepository.findById(childId).orElseThrow(() -> new ChildNotFoundException("해당 자녀를 찾을 수 없습니다."));

        List<Diet> dietList = new ArrayList<>();
        List<DietMaterial> dietMaterialList = new ArrayList<>();
        List<DietMenu> dietMenuList = new ArrayList<>();

        for (int day = 0; day < 7; day++) {
            RedisRecommendDiet recommendDiet = redisRecommendDietList.get(day);
            for (int sequence = 0; sequence < recommendDiet.getDietList().size(); sequence++) {
                // Diet DB 추가
                Diet diet = Diet.builder()
                        .dietDate(startDate.plusDays(day))
                        .mealTime(MealTime.NONE)
                        .diary(null)
                        .child(child)
                        .build();
                dietList.add(diet);

                recommendDiet.getDietList().get(sequence).getMenuList().forEach(id -> {
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

    private List<InitDietResponse> getInitRecommend(Child child, List<Integer> allergyList, List<String> pastMenu) {

        List<String> allergyName = allergyRepository.findAllergyNameByIdIn(allergyList).stream().map(Allergy::getAllergyName).toList();

        RecommendRequest recommendRequest = RecommendRequest.builder()
                .childId(child.getId())
                .allergyList(allergyName)
                .cacheList(pastMenu)
                .need(getChildNeed(child))
                .needType(null)
                .currentMenuIdOfDiet(null)
                .build();

        List<List<LinkedHashMap<String, Object>>> recommendResult = fastAPIConnectionUtil.postApiConnectionResult("/init", recommendRequest, new ArrayList<>());

        return convertInitRecommend(recommendResult);
    }

    private List<InitDietResponse> convertInitRecommend(List<List<LinkedHashMap<String, Object>>> recommendResult) {
        List<List<MenuDetailResponse>> recommendList = recommendResult.stream().map(
                diet -> diet.stream().map(this::convertPythonRecommendAPIToResponse).toList()).toList();

        List<InitDietResponse> initDietResponseList = new ArrayList<>();
        for (int day = 0; day < 7; day++) {
            List<MenuDetailResponse> recommend = recommendList.get(day);

            List<DietMenuResponse> menuList = recommend.stream().map(
                    menu -> DietMenuResponse.builder()
                            .menuId(menu.getMenuId())
                            .menuName(menu.getMenuName())
                            .menuType(menu.getMenuType())
                            .build()
            ).toList();

            List<List<DietMenuResponse>> menuWrapper = new ArrayList<>();
            menuWrapper.add(menuList);
            initDietResponseList.add(InitDietResponse.builder()
                    .dietDay(day)
                    .menuList(menuWrapper)
                    .build());
        }

        return initDietResponseList;
    }

    private List<DietMenuResponse> getTempRecommend(Integer childId, List<Integer> allergyList, List<String> doNotRecommend) {
        Child child = childRepository.findById(childId).orElseThrow(() -> new ChildNotFoundException("해당 ID의 자녀를 찾을 수 없습니다."));

        List<String> allergyName = allergyRepository.findAllergyNameByIdIn(allergyList).stream().map(Allergy::getAllergyName).toList();

        RecommendRequest recommendRequest = RecommendRequest.builder()
                .childId(childId)
                .allergyList(allergyName)
                .cacheList(doNotRecommend)
                .need(getChildNeed(child))
                .needType(null)
                .currentMenuIdOfDiet(null)
                .build();

        List<LinkedHashMap<String, Object>> recommendResult = fastAPIConnectionUtil.postApiConnectionResult("", recommendRequest, new ArrayList<>());
        return convertTempRecommend(recommendResult);
    }

    private List<DietMenuResponse> convertTempRecommend(List<LinkedHashMap<String, Object>> recommendResult) {
        List<MenuDetailResponse> recommendList = recommendResult.stream()
                .map(this::convertPythonRecommendAPIToResponse).toList();


        return recommendList.stream().map(
                menu -> DietMenuResponse.builder()
                        .menuId(menu.getMenuId())
                        .menuName(menu.getMenuName())
                        .menuType(menu.getMenuType())
                        .build()
        ).toList();
    }

    private MenuDetailResponse getMenuRecommend(Integer childId, List<Integer> allergyList, List<String> doNotRecommend, List<String> menuList, String refreshMenuId) {
        Child child = childRepository.findById(childId).orElseThrow(() -> new ChildNotFoundException("해당 ID의 자녀를 찾을 수 없습니다."));

        List<String> allergyName = allergyRepository.findAllergyNameByIdIn(allergyList).stream().map(Allergy::getAllergyName).toList();

        MenuType needType = fastAPIConnectionUtil.getApiConnectionResult("/info/" + refreshMenuId, DietMenuResponse.builder().build()).getMenuType();

        return convertPythonRecommendAPIToResponse(
                fastAPIConnectionUtil.postApiConnectionResult("/menu", RecommendRequest.builder()
                        .childId(childId)
                        .allergyList(allergyName)
                        .cacheList(doNotRecommend)
                        .need(getChildNeed(child))
                        .needType(needType)
                        .currentMenuIdOfDiet(menuList)
                        .build(), new LinkedHashMap<>()
                )
        );
    }

    private RecommendNeedDto getChildNeed(Child child) {
        int age = Period.between(child.getBirthDate(), LocalDate.now()).getYears();
        int growMonth = Period.between(child.getBirthDate(), LocalDate.now()).getMonths() + (age * 12);
        double bmi = child.getWeight() / Math.pow(child.getHeight(), 2) * 10000;

        double obesityBmi = bmiConditionRepository.findAllByGenderAndGrowMonthAndWeightCondition(child.getGender(), growMonth, WeightCondition.HIGH_WEIGHT).getStandard();

        DietNutrition dietNutrition = DietNutrition.getNutrition(age, child.getGender(), bmi >= obesityBmi);

        return RecommendNeedDto.builder()
                .calories(dietNutrition.getCalorie())
                .carbohydrate(dietNutrition.getCarbonHydrate())
                .protein(dietNutrition.getProtein())
                .cellulose(dietNutrition.getDietFiber())
                .build();
    }

    private List<Integer> convertCookieStringToIntegerList(String cookie) {
        List<Integer> result = new ArrayList<>();
        if(!cookie.isEmpty()) {
            String[] cookieSplit = cookie.split("\\|");
            try {
                Arrays.stream(cookieSplit)
                        .forEach(id -> result.add(Integer.parseInt(id)));
            } catch (NumberFormatException e) {
                throw new WrongCookieDataException("쿠키에 잘못된 값이 입력되었습니다.");
            }
        }

        return result;

    }

    private List<String> convertCookieStringToStringList(String cookie) {
        List<String> result;
        String[] cookieSplit = cookie.split("\\|");
        try {
            result = new ArrayList<>(Arrays.asList(cookieSplit));
        } catch (Exception e) {
            throw new WrongCookieDataException("쿠키에 잘못된 값이 입력되었습니다.");
        }
        return result;
    }

    private MenuDetailResponse convertPythonRecommendAPIToResponse(LinkedHashMap<String, Object> recommendResult) {
        return MenuDetailResponse.builder()
                .menuId((String) recommendResult.get("menu_id"))
                .menuName((String) recommendResult.get("menu_name"))
                .menuImgUrl((String) recommendResult.get("img_url"))
                .menuType(MenuType.find((String) recommendResult.get("type")))
                .calorie(Integer.parseInt((String) recommendResult.get("cal")))
                .carbohydrate(Double.parseDouble((String) recommendResult.get("carbohydrate")))
                .protein(Double.parseDouble((String) recommendResult.get("protein")))
                .fat(Double.parseDouble((String) recommendResult.get("fat")))
                .materials((List<String>) recommendResult.get("material"))
                .recipe((List<String>) ((Map<String, Object>) recommendResult.get("recipe")).get("content_list"))
                .need((String) ((Map<String, Object>) recommendResult.get("recipe")).get("need"))
                .build();
    }

    private List<MenuDetailResponse> convertPythonInfoAPIToResponse(List<LinkedHashMap<String, Object>> infoResult) {
        return infoResult.stream().map(menuInfo -> MenuDetailResponse.builder()
                .menuId((String) menuInfo.get("menuId"))
                .menuName((String) menuInfo.get("menuName"))
                .menuImgUrl((String) menuInfo.get("menuImgUrl"))
                .menuType(MenuType.valueOf((String) menuInfo.get("menuType")))
                .calorie((int) menuInfo.get("calorie"))
                .carbohydrate((double) menuInfo.get("carbohydrate"))
                .protein((double) menuInfo.get("protein"))
                .fat((double) menuInfo.get("fat"))
                .materials((List<String>) menuInfo.get("materials"))
                .recipe((List<String>) menuInfo.get("recipe"))
                .need((String) menuInfo.get("need"))
                .build()).toList();
    }
}
