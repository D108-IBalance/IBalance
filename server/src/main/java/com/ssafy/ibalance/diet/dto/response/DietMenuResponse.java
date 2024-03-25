package com.ssafy.ibalance.diet.dto.response;

import com.ssafy.ibalance.diet.type.MenuType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DietMenuResponse {

    private String menuId;
    private String menuName;
    private MenuType menuType;
}
