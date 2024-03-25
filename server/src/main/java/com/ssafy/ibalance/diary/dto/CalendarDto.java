package com.ssafy.ibalance.diary.dto;

import com.ssafy.ibalance.member.entity.Member;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDto {

    private Member member;
    private LocalDate dietDate;
    private boolean allReviewed;
}
