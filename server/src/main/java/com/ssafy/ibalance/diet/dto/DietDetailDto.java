package com.ssafy.ibalance.diet.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.entity.DietMaterial;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DietDetailDto {

    private Child child;
    private Diet diet;
    private List<String> menuIdList;
    private List<DietMaterial> dietMaterialList;

    @QueryProjection
    public DietDetailDto(Child child, Diet diet, List<String> menuIdList, List<DietMaterial> dietMaterialList) {
        this.child = child;
        this.diet = diet;
        this.menuIdList = menuIdList;
        this.dietMaterialList = dietMaterialList;
    }
}
