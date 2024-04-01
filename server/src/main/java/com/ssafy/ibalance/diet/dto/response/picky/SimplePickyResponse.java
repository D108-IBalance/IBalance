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
public class SimplePickyResponse {
    private String pickyMaterialName;
    private List<SimpleRecipeResponse> recipes;
}
