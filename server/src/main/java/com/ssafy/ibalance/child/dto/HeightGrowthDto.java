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
public class HeightGrowthDto {

    private Integer weekCreatedTime;
    private LocalDateTime createdTime;
    private double height;
    private Child child;
}
