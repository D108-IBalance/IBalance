package com.ssafy.ibalance.diet.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class RecommendResponse {

    private String menuId;
    private String menuName;
    private String menuImgUrl;
    private String menuType;
    private int calorie;
    private double carbohydrate;
    private double protein;
    private double fat;
    private List<String> materials;
    private List<String> recipeList;
    private String need;
}
