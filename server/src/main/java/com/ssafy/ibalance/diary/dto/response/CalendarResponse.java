package com.ssafy.ibalance.diary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarResponse {
    private LocalDate dietDate;
    private boolean allReviewed;
}
