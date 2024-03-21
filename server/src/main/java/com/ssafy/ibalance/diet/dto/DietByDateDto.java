package com.ssafy.ibalance.diet.dto;

import com.ssafy.ibalance.diet.entity.DietMenu;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class DietByDateDto {
    private Long dietId;
    private LocalDate dietDate;
    private Integer sequence;
    private List<DietMenu> dietMenuList;
}