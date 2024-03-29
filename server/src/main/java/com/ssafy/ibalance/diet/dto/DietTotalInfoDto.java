package com.ssafy.ibalance.diet.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.entity.DietMaterial;
import com.ssafy.ibalance.diet.entity.DietMenu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DietTotalInfoDto {

    private Diet diet;
    private List<DietMaterial> dietMaterialList;
    private List<DietMenu> dietMenuList;

    @QueryProjection
    public DietTotalInfoDto(Diet diet, List<DietMaterial> dietMaterialList, List<DietMenu> dietMenuList) {
        this.diet = diet;
        this.dietMaterialList = dietMaterialList;
        this.dietMenuList = dietMenuList;
    }
}
