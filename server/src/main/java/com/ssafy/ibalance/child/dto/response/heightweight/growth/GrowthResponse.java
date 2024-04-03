package com.ssafy.ibalance.child.dto.response.heightweight.growth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.ibalance.child.dto.GrowthDto;
import com.ssafy.ibalance.child.type.Gender;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public static <T extends GrowthResponse> T convertEntityToDto(GrowthDto growth, T target) {
        LocalDate recordDate = growth.getCreatedTime().toLocalDate();

        LocalDate endDate = recordDate.plusDays(7).with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY));
        if(recordDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            endDate = recordDate;
        }

        target.setGender(growth.getChild().getGender());
        target.setBirthDate(growth.getChild().getBirthDate());
        target.setMonth(ChronoUnit.MONTHS.between(growth.getChild().getBirthDate(), recordDate));
        target.setRecordDate(recordDate);
        target.setStartDate(recordDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)));
        target.setEndDate(endDate);
        return target;
    }
}
