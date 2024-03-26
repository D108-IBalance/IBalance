package com.ssafy.ibalance.diet.dto;

import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.entity.DietMenu;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class DietByDateDto {

    private Diet diet;
    private List<DietMenu> dietMenuList;
}
