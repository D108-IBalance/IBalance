package com.ssafy.ibalance.child.dto.response;

import com.ssafy.ibalance.child.entity.AverageGrowth;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AverageGrowthResponse {
    private int month;
    private double averageHeight;
    private double averageWeight;

    public static AverageGrowthResponse ConvertEntityToDto(AverageGrowth averageGrowth) {
        return builder()
                .month(averageGrowth.getMonth())
                .averageHeight(averageGrowth.getHeight())
                .averageWeight(averageGrowth.getWeight())
                .build();
    }
}
