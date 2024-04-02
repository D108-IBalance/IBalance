package com.ssafy.ibalance.child.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.ibalance.child.dto.WeightGrowthDto;
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
public class WeightGrowthResponse {

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
    private double weight;

    public static WeightGrowthResponse ConvertEntityToDto(WeightGrowthDto weightGrowth) {
        LocalDate recordDate = weightGrowth.getCreatedTime().toLocalDate();

        LocalDate endDate = recordDate.plusDays(7).with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY));
        if(recordDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            endDate = recordDate;
        }

        return builder()
                .gender(weightGrowth.getChild().getGender())
                .birthDate(weightGrowth.getChild().getBirthDate())
                .month(ChronoUnit.MONTHS.between(weightGrowth.getChild().getBirthDate(), recordDate))
                .recordDate(recordDate)
                .startDate(recordDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)))
                .endDate(endDate)
                .weight(weightGrowth.getWeight())
                .build();
    }
}
