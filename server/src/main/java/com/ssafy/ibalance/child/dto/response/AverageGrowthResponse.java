package com.ssafy.ibalance.child.dto.response;

import com.ssafy.ibalance.child.entity.AverageGrowth;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AverageGrowthResponse {
    private Integer month;
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
