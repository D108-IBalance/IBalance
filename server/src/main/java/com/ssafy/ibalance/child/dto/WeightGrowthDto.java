package com.ssafy.ibalance.child.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeightGrowthDto extends GrowthDto {

    private double weight;
}
