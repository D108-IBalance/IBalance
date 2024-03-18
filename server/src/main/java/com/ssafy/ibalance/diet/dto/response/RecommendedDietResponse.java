package com.ssafy.ibalance.diet.dto.response;

import com.ssafy.ibalance.diet.dto.MenuDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
public class RecommendedDietResponse {

    private Long dietId;
    private LocalDate dietDate;
    private int sequence;
    private List<MenuDto> menuList;
}
