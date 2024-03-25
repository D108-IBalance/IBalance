package com.ssafy.ibalance.diet.dto.response;

import com.ssafy.ibalance.diet.type.MenuType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MenuDetailResponse {

    private Integer menuId;
    private String menuName;
    private String menuImgUrl;
    private MenuType menuType;
    private int calorie;
    private double carbohydrate;
    private double protein;
    private double fat;
    private List<String> materials;
    private List<String> recipe;
}
