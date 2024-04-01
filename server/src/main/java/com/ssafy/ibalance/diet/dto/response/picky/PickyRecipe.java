package com.ssafy.ibalance.diet.dto.response.picky;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PickyRecipe {
    private String recipeTitle;
    private String recipeImgUrl;
    private List<RecipeMaterial> recipeMaterialList;
    private List<RecipeStep> recipeSteps;
    private String recipeId;
}
