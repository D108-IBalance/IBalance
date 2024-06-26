package com.ssafy.ibalance.common.util;

import com.ssafy.ibalance.diet.dto.response.DietMenuResponse;
import com.ssafy.ibalance.diet.dto.response.MenuDetailResponse;
import com.ssafy.ibalance.diet.exception.WrongCookieDataException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CookieUtil {

    public void initCookie(HttpServletRequest request, HttpServletResponse response) {
        if(request.getCookies() == null) {
            return;
        }
        Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("allergy"))
                .forEach(cookie -> {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                });
    }

    public <T> void makeCookie(HttpServletResponse response, List<T> target, String cookieName, String cookiePath) {
        StringBuilder sb = new StringBuilder();
        target.forEach(targetInfo -> sb.append(targetInfo).append("|"));

        Cookie cookie = new Cookie(cookieName, sb.toString());
        cookie.setPath(cookiePath);
        response.addCookie(cookie);
    }

    public String getCookie(HttpServletRequest request, String cookieName) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .map(Cookie::getValue).findFirst()
                .orElseThrow(() -> new WrongCookieDataException("필요한 쿠키가 없습니다."));
    }

    public void addCookieValue(HttpServletRequest request, HttpServletResponse response, List<DietMenuResponse> menuList, String cookieName, String setPath) {
        Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .forEach(cookie -> {
                    String beforeValue = cookie.getValue();
                    StringBuilder stringBuilder = new StringBuilder(beforeValue);
                    menuList.forEach(menu -> stringBuilder.append(menu.getMenuId()).append("|"));

                    Cookie createCookie = new Cookie(cookieName, stringBuilder.toString());
                    createCookie.setPath(setPath);
                    response.addCookie(createCookie);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                });
    }

    public void addCookieValue(HttpServletRequest request, HttpServletResponse response, MenuDetailResponse menuDetailResponse, String cookieName, String setPath) {
        Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .forEach(cookie -> {
                    String beforeValue = cookie.getValue();
                    String result = beforeValue + menuDetailResponse.getMenuId() + "|";

                    Cookie createCookie = new Cookie(cookieName, result);
                    createCookie.setPath(setPath);
                    response.addCookie(createCookie);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                });
    }
}
