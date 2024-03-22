package com.ssafy.ibalance.diet.controller;

import com.ssafy.ibalance.diet.dto.MenuDetailDto;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import com.ssafy.ibalance.diet.dto.response.InitDietResponse;
import com.ssafy.ibalance.diet.service.DietService;
import jakarta.servlet.http.Cookie;
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
    public List<MenuDetailDto> getDietDetail(@PathVariable Long dietId) {
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
    public List<InitDietResponse> getInitDiet(@PathVariable Integer childId, HttpServletResponse response) {
        List<Integer> allergyList = dietService.getAllergy(childId);
        makeCookie(response, allergyList, "allergy", "/");

        List<Integer> pastMenu = dietService.getPastMenu(childId);
        makeCookie(response, pastMenu, "doNotRecommend", "/");

        return dietService.getInitDiet(childId, allergyList, pastMenu);
    }

    /**
     * 쿠키에 저장하기 위한 데이터 변환
     *
     * @param response 쿠키 저장을 위한 response
     * @param target 쿠키 변환 대상
     * @param cookieName 쿠키 이름
     * @param cookiePath 쿠키가 사용될 경로
     */
    private void makeCookie(HttpServletResponse response, List<Integer> target, String cookieName, String cookiePath) {
        StringBuilder sb = new StringBuilder();
        for(Integer targetInt : target) {
            sb.append(targetInt);
            sb.append("|");
        }

        Cookie cookie = new Cookie(cookieName, sb.toString());
        cookie.setPath(cookiePath);
        response.addCookie(cookie);
    }
}
