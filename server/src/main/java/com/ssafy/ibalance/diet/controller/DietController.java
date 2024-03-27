package com.ssafy.ibalance.diet.controller;

import com.ssafy.ibalance.common.util.CookieUtil;
import com.ssafy.ibalance.diet.dto.response.MenuDetailResponse;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import com.ssafy.ibalance.diet.dto.response.DietMenuResponse;
import com.ssafy.ibalance.diet.dto.response.InitDietResponse;
import com.ssafy.ibalance.diet.service.DietService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 박성진
 */
@RestController
@RequestMapping("diet")
@RequiredArgsConstructor
public class DietController {

    private final DietService dietService;
    private final CookieUtil cookieUtil;

    /**
     * 일주일치 확정된 식단 조회
     *
     * @param childId 자녀 아이디
     * @param today 오늘 날짜
     * @return 일주일 식단 목록
     */
    @GetMapping("/{childId}")
    public List<DietByDateResponse> getRecommendedDiet(@PathVariable Integer childId, @RequestParam LocalDate today) {
        return dietService.getRecommendedDiet(childId, today);
    }

    /**
     * 식단 상세 조회
     *
     * @param dietId 식단 아이디
     * @return 메뉴 상세 정보 목록
     */
    @GetMapping("/detail/{dietId}")
    public List<MenuDetailResponse> getDietDetail(@PathVariable Long dietId) {
        return dietService.getDietDetail(dietId);
    }

    /**
     * 일주일치 초기 추천된 식단 목록
     *
     * @param childId 자녀 아이디
     * @param response 쿠키 저장을 위한 response
     * @return 추천된 7개의 식단 정보
     */
    @GetMapping("/{childId}/init")
    public List<InitDietResponse> getInitDiet(@PathVariable Integer childId, HttpServletRequest request, HttpServletResponse response) {
        cookieUtil.initCookie(request, response);

        List<Integer> allergyList = dietService.getAllergy(childId);
        cookieUtil.makeIntegerCookie(response, allergyList, "allergy", "/");

        List<String> pastMenu = dietService.getPastMenu(childId);
        List<InitDietResponse> initDietResponseList = dietService.getInitDiet(childId, allergyList, pastMenu);
        cookieUtil.makeStringCookie(response, pastMenu, "doNotRecommend", "/");

        return initDietResponseList;
    }

    /**
     * 한 끼 식단 추가 추천
     *
     * @param childId 자녀 아이디
     * @param dietDay 추천 식단을 추가할 날의 번째 (0~6)
     * @param request 저장된 쿠키를 가져오기 위한 request
     * @param response 쿠키 저장을 위한 response
     * @return 한 끼 식단으로 추천된 메뉴 목록
     */
    @GetMapping("/{childId}/temp")
    public List<DietMenuResponse> addTempDiet(@PathVariable Integer childId, @RequestParam int dietDay, HttpServletRequest request, HttpServletResponse response) {
        String allergy = cookieUtil.getCookie(request, "allergy");
        String doNotRecommend = cookieUtil.getCookie(request, "doNotRecommend");
        List<DietMenuResponse> menuList = dietService.addTempDiet(childId, dietDay, allergy, doNotRecommend);
        cookieUtil.addCookieValue(request, response, menuList, "doNotRecommend", "/");
        return menuList;
    }

    /**
     * 추천중인 식단 중 한 끼 식단을 제거
     *
     * @param childId 자녀 아이디
     * @param dietDay 삭제할 추천 식단의 날의 번째 (0~6)
     * @param sequence 삭제할 추천 식단의 같은 일자 내의 번째 (0~2)
     * @return 삭제된 식단의 메뉴 아이디 목록
     */
    @DeleteMapping("/{childId}/temp")
    public List<String> deleteTempDiet(@PathVariable Integer childId, @RequestParam int dietDay, @RequestParam int sequence) {
        return dietService.deleteTempDiet(childId, dietDay, sequence);
    }

    /**
     * 한 끼 식단에서 하나의 메뉴만 새로고침
     *
     * @param childId 자녀 아이디
     * @param dietDay 삭제할 추천 식단의 날의 번째 (0~6)
     * @param sequence 삭제할 추천 식단의 같은 일자 내의 번째 (0~2)
     * @param menuId 삭제할 메뉴의 아이디
     * @param request 저장된 쿠키를 가져오기 위한 request
     * @param response 쿠키 저장을 위한 response
     * @return 추천받은 메뉴 정보
     */
    @PutMapping("/{childId}/temp")
    public MenuDetailResponse changeMenuOfTempDiet(@PathVariable Integer childId, @RequestParam int dietDay, @RequestParam int sequence, @RequestParam String menuId, HttpServletRequest request, HttpServletResponse response) {
        String allergy = cookieUtil.getCookie(request, "allergy");
        String doNotRecommend = cookieUtil.getCookie(request, "doNotRecommend");
        MenuDetailResponse menu = dietService.changeMenuOfTempDiet(childId, dietDay, sequence, menuId, allergy, doNotRecommend);
        cookieUtil.addCookieValue(request, response, menu, "doNotRecommend", "/");
        return menu;
    }

    /**
     * 추천받고 있는 식단을 확정
     *
     * @param childId 자녀 아이디
     * @param startDate 시작일자
     * @return 식단 아이디 목록
     */
    @GetMapping("/{childId}/insert")
    public List<Long> insertTempDiet(@PathVariable Integer childId, @RequestParam LocalDate startDate) {
        return dietService.insertTempDiet(childId, startDate);
    }
}
