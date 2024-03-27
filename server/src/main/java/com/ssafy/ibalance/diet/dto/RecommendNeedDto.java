package com.ssafy.ibalance.diet.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RecommendNeedDto {

    private Integer calories;
    private double carbohydrate;
    private double protein;
    private double cellulose;
}
