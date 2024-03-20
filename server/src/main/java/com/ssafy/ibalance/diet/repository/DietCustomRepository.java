package com.ssafy.ibalance.diet.repository;

import com.ssafy.ibalance.diary.dto.response.CalendarResponse;
import com.ssafy.ibalance.diet.dto.response.RecommendedDietResponse;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import com.ssafy.ibalance.member.entity.Member;

import java.time.LocalDate;
import java.util.List;

public interface DietCustomRepository {
    List<DietByDateResponse> getDietByDate(Integer childId, LocalDate date);

    List<RecommendedDietResponse> getDietMenuByDate(Integer childId, LocalDate startDate, LocalDate endDate);

    List<Integer> getMenuIdByDietId(Long dietId);

    List<CalendarResponse> getCalendarList(Integer childId, int year, int month, Member member);
}
