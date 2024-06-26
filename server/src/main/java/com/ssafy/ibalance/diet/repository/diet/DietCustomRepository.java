package com.ssafy.ibalance.diet.repository.diet;

import com.ssafy.ibalance.diary.dto.response.CalendarResponse;
import com.ssafy.ibalance.diet.dto.DietTotalInfoDto;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import com.ssafy.ibalance.member.entity.Member;

import java.time.LocalDate;
import java.util.List;

public interface DietCustomRepository {

    List<DietByDateResponse> getDietByDate(Integer childId, LocalDate date, Member member);

    List<DietByDateResponse> getDietByDateBetween(Integer childId, LocalDate startDate, LocalDate endDate);

    List<String> getMenuIdByDietId(Long dietId);

    List<CalendarResponse> getCalendarList(Integer childId, int year, int month, Member member);

    DietTotalInfoDto getDietTotalInfo(Long dietId);

    List<Integer> getNotifyTargetList();
}
