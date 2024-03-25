package com.ssafy.ibalance.common.util;

import com.ssafy.ibalance.diet.dto.response.DietMenuResponse;
import com.ssafy.ibalance.diet.dto.response.MenuDetailResponse;
import com.ssafy.ibalance.diet.exception.WrongCookieDataException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CookieUtil {

    public void initCookie(HttpServletRequest request, HttpServletResponse response) {
        if(request.getCookies() == null) return;
        for(Cookie cookie : request.getCookies()) {
            if(cookie.getName().equals("allergy") || cookie.getName().equals("doNotRecommend")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    public void makeCookie(HttpServletResponse response, List<Integer> target, String cookieName, String cookiePath) {
        StringBuilder sb = new StringBuilder();
        for(Integer targetInt : target) {
            sb.append(targetInt);
            sb.append("|");
        }

        Cookie cookie = new Cookie(cookieName, sb.toString());
        cookie.setPath(cookiePath);
        response.addCookie(cookie);
    }

    public String getCookie(HttpServletRequest request, String cookieName) {
        for(Cookie cookie : request.getCookies()) {
            if(cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        throw new WrongCookieDataException("필요한 쿠키가 없습니다.");
    }

    public void addCookieValue(HttpServletRequest request, HttpServletResponse response, List<DietMenuResponse> menuList, String cookieName, String setPath) {
        for(Cookie cookie : request.getCookies()) {
            if(cookie.getName().equals(cookieName)) {
                String beforeValue = cookie.getValue();
                StringBuilder stringBuilder = new StringBuilder(beforeValue);
                for (DietMenuResponse menu : menuList) {
                    stringBuilder.append(menu.getMenuId());
                    stringBuilder.append("|");
                }

                Cookie createCookie = new Cookie(cookieName, stringBuilder.toString());
                createCookie.setPath(setPath);
                response.addCookie(createCookie);
                cookie.setMaxAge(0);
                response.addCookie(cookie);

                break;
            }
        }
    }

    public void addCookieValue(HttpServletRequest request, HttpServletResponse response, MenuDetailResponse menuDetailResponse, String cookieName, String setPath) {
        for(Cookie cookie : request.getCookies()) {
            if(cookie.getName().equals(cookieName)) {
                String beforeValue = cookie.getValue();
                String result = beforeValue + menuDetailResponse.getMenuId() + "|";

                Cookie createCookie = new Cookie(cookieName, result);
                createCookie.setPath(setPath);
                response.addCookie(createCookie);
                cookie.setMaxAge(0);
                response.addCookie(cookie);

                break;
            }
        }
    }
}
