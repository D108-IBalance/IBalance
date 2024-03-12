package com.ssafy.ibalance.diet.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.entity.DietMenu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class DietByDateDto {
    private Diet diet;
    private List<DietMenu> dietMenuList;

    @QueryProjection
    public DietByDateDto(Diet diet, List<DietMenu> dietMenuList) {
        this.diet = diet;
        this.dietMenuList = dietMenuList;
    }
}
