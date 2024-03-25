package com.ssafy.ibalance.diet.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RecipeDto {

    private String recipe;
    private String recipeImg;
}
