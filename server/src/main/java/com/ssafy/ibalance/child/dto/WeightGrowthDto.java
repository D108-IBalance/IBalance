package com.ssafy.ibalance.child.dto;

import com.ssafy.ibalance.child.entity.Child;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeightGrowthDto {

    private Integer weekCreatedTime;
    private LocalDateTime createdTime;
    private double weight;
    private Child child;
}
