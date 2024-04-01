package com.ssafy.ibalance.diet.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RecommendedMenuResponse {

    private Long dietId;
    private List<DietMenuResponse> menuList;
}
