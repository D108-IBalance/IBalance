package com.ssafy.ibalance.diary.dto;

import com.ssafy.ibalance.child.entity.Child;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDto {

    private Child child;
    private LocalDate dietDate;
    private boolean allReviewed;
}
