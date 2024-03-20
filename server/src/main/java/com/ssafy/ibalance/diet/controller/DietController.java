package com.ssafy.ibalance.diet.controller;

import com.ssafy.ibalance.diet.dto.MenuDetailDto;
import com.ssafy.ibalance.diet.dto.response.InitDietResponse;
import com.ssafy.ibalance.diet.dto.response.RecommendedDietResponse;
import com.ssafy.ibalance.diet.service.DietService;
import jakarta.servlet.http.Cookie;
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

    @GetMapping("/{childId}")
    public List<RecommendedDietResponse> getRecommendedDiet(@PathVariable Integer childId, @RequestParam LocalDate today) {
        return dietService.getRecommendedDiet(childId, today);
    }

    @GetMapping("/detail/{dietId}")
    public List<MenuDetailDto> getDietDetail(@PathVariable Long dietId) {
        return dietService.getDietDetail(dietId);
    }

    @GetMapping("/{childId}/init")
    public List<InitDietResponse> getInitDiet(@PathVariable Integer childId, HttpServletResponse response) {
        List<Integer> allergyList = dietService.getAllergy(childId);
        makeCookie(response, allergyList, "allergy", "/");

        List<Integer> pastMenu = dietService.getPastMenu(childId);
        makeCookie(response, pastMenu, "doNotRecommend", "/");

        return dietService.getInitDiet(childId, allergyList, pastMenu);
    }

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
