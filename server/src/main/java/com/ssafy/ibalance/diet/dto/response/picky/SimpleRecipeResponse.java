package com.ssafy.ibalance.diet.dto.response.picky;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleRecipeResponse {
    private String recipeTitle;
    private String recipeImgUrl;
    private String recipeId;
}
