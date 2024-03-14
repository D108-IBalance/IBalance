package com.ssafy.ibalance.growth.dto.response;

import com.ssafy.ibalance.growth.entity.Growth;
import lombok.Builder;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Builder
@Getter
public class GrowthResponse {
    private LocalDate recordDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double height;
    private Double weight;

    public static GrowthResponse ConvertEntityToDto(Growth growth) {
        LocalDate recordDate = growth.getCreatedTime().toLocalDate();
        return builder()
                .recordDate(recordDate)
                .startDate(recordDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)))
                .endDate(recordDate.plusDays(7).with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY)))
                .height(growth.getHeight())
                .weight(growth.getWeight())
                .build();
    }
}
