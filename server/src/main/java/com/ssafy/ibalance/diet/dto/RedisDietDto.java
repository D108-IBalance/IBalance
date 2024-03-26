package com.ssafy.ibalance.diet.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RedisDietDto {

    private List<String> menuList;
}
