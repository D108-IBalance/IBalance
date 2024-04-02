package com.ssafy.ibalance.child.dto;

import com.ssafy.ibalance.child.entity.Child;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GrowthDto {
    private Integer weekCreatedTime;
    private LocalDateTime createdTime;
    private Child child;
}
