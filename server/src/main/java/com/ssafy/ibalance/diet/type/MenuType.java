package com.ssafy.ibalance.diet.type;

import lombok.Getter;
import org.springframework.web.server.NotAcceptableStatusException;

import java.util.Arrays;

@Getter
public enum MenuType {
    RICE("밥"), SOUP("국"), SIDE("반찬");

    private String menuType;

    private MenuType(String menuType) {
        this.menuType = menuType;
    }

    public static MenuType find(String menuType) {
        return Arrays.stream(values())
                .filter(accountStatus -> accountStatus.menuType.equals(menuType))
                .findAny()
                .orElseThrow(() -> new NotAcceptableStatusException("MenuType이 올바르지 않습니다."));
    }

}
