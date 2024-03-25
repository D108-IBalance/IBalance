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

@RestController
@RequestMapping("diet")
@RequiredArgsConstructor
public class DietController {

    private final DietService dietService;
    private final CookieUtil cookieUtil;

    @GetMapping("/{childId}")
    public List<DietByDateResponse> getRecommendedDiet(@PathVariable Integer childId, @RequestParam LocalDate today) {
        return dietService.getRecommendedDiet(childId, today);
    }

    @GetMapping("/detail/{dietId}")
    public List<MenuDetailResponse> getDietDetail(@PathVariable Long dietId) {
        return dietService.getDietDetail(dietId);
    }

    @GetMapping("/{childId}/init")
    public List<InitDietResponse> getInitDiet(@PathVariable Integer childId, HttpServletRequest request, HttpServletResponse response) {
        cookieUtil.initCookie(request, response);

        List<Integer> allergyList = dietService.getAllergy(childId);
        cookieUtil.makeCookie(response, allergyList, "allergy", "/");

        List<Integer> pastMenu = dietService.getPastMenu(childId);
        List<InitDietResponse> initDietResponseList = dietService.getInitDiet(childId, allergyList, pastMenu);
        cookieUtil.makeCookie(response, pastMenu, "doNotRecommend", "/");

        return initDietResponseList;
    }

    @GetMapping("/{childId}/temp")
    public List<DietMenuResponse> addTempDiet(@PathVariable Integer childId, @RequestParam int dietDay, HttpServletRequest request, HttpServletResponse response) {
        String allergy = cookieUtil.getCookie(request, "allergy");
        String doNotRecommend = cookieUtil.getCookie(request, "doNotRecommend");
        List<DietMenuResponse> menuList = dietService.addTempDiet(childId, dietDay, allergy, doNotRecommend);
        cookieUtil.addCookieValue(request, response, menuList, "doNotRecommend", "/");
        return menuList;
    }

    @DeleteMapping("/{childId}/temp")
    public List<Integer> deleteTempDiet(@PathVariable Integer childId, @RequestParam int dietDay, @RequestParam int sequence) {
        return dietService.deleteTempDiet(childId, dietDay, sequence);
    }

    @PutMapping("/{childId}/temp")
    public MenuDetailResponse changeMenuOfTempDiet(@PathVariable Integer childId, @RequestParam int dietDay, @RequestParam int sequence, @RequestParam Integer menuId, HttpServletRequest request, HttpServletResponse response) {
        String allergy = cookieUtil.getCookie(request, "allergy");
        String doNotRecommend = cookieUtil.getCookie(request, "doNotRecommend");
        MenuDetailResponse menu = dietService.changeMenuOfTempDiet(childId, dietDay, sequence, menuId, allergy, doNotRecommend);
        cookieUtil.addCookieValue(request, response, menu, "doNotRecommend", "/");
        return menu;
    }

    @GetMapping("/{childId}/insert")
    public List<Long> insertTempDiet(@PathVariable Integer childId, @RequestParam LocalDate startDate) {
        return dietService.insertTempDiet(childId, startDate);
    }
}
