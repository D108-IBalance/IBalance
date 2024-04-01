package com.ssafy.ibalance.diet.dto;

import com.ssafy.ibalance.diet.type.MenuType;
import com.ssafy.ibalance.material.dto.MaterialDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MenuDetailDto {

    private Integer menuId;
    private String menuName;
    private String menuImgUrl;
    private MenuType menuType;
    private int calorie;
    private double carbohydrate;
    private double protein;
    private double fat;
    private List<MaterialDto> materials;
    private List<RecipeDto> recipe;
}
