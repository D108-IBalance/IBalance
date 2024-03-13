package com.ssafy.ibalance.diet.dto.response;

import com.ssafy.ibalance.diet.entity.DietMenu;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class DietByDateResponse {
    private Long dietId;
    private LocalDate dietDate;
    private Integer sequence;
    private List<DietMenu> dietMenuList;
}
