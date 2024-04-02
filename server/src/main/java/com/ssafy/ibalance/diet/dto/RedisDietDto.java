package com.ssafy.ibalance.diet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class RedisDietDto {

    private List<String> menuList;
}
