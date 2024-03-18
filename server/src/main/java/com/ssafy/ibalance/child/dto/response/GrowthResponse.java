package com.ssafy.ibalance.child.dto.response;

import com.ssafy.ibalance.child.entity.Growth;
import com.ssafy.ibalance.child.type.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

@Builder
@Getter
@Setter
public class GrowthResponse {
    private Gender gender;
    private LocalDate birthDate;
    private long month;
    private LocalDate recordDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private double height;
    private double weight;

    public static GrowthResponse ConvertEntityToDto(Growth growth) {
        LocalDate recordDate = growth.getCreatedTime().toLocalDate();
        return builder()
                .gender(growth.getChild().getGender())
                .birthDate(growth.getChild().getBirthDate())
                .month(ChronoUnit.MONTHS.between(growth.getChild().getBirthDate(), recordDate))
                .recordDate(recordDate)
                .startDate(recordDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)))
                .endDate(recordDate.plusDays(7).with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY)))
                .height(growth.getHeight())
                .weight(growth.getWeight())
                .build();
    }
}
