package com.ssafy.ibalance.diet.dto;

import com.ssafy.ibalance.diet.type.MenuType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MenuDto {

    private Integer menuId;
    private String menuName;
    private MenuType menuType;
}