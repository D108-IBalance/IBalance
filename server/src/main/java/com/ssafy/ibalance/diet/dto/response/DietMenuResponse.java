package com.ssafy.ibalance.diet.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DietMenuResponse {
    private Integer menuId;
    private String menuName;
    private Float score;
}
