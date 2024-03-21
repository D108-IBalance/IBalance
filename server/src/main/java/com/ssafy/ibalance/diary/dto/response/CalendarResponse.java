package com.ssafy.ibalance.diary.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.ibalance.diary.dto.CalendarDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class CalendarResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dietDate;
    private boolean allReviewed;

    public static CalendarResponse convertEntityToDto(CalendarDto calendarDto) {
        return CalendarResponse.builder()
                .dietDate(calendarDto.getDietDate())
                .allReviewed(calendarDto.isAllReviewed())
                .build();
    }
}
