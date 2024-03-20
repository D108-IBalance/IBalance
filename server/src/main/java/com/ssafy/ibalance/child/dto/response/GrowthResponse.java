package com.ssafy.ibalance.child.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private long month;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate recordDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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
