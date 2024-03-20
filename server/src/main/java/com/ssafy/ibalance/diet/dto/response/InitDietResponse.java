package com.ssafy.ibalance.diet.dto.response;

import com.ssafy.ibalance.diet.dto.MenuDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class InitDietResponse {
    private int dietDay;
    private List<MenuDto> menuList;
}
