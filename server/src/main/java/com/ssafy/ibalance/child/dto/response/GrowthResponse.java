package com.ssafy.ibalance.child.dto.response;

import com.ssafy.ibalance.child.entity.Growth;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class GrowthResponse {
    private LocalDate recordDate;
    private Double height;
    private Double weight;

    public static GrowthResponse ConvertEntityToDto(Growth growth) {
        return builder()
                .recordDate(growth.getCreatedTime().toLocalDate())
                .height(growth.getHeight())
                .weight(growth.getWeight())
                .build();
    }
}
