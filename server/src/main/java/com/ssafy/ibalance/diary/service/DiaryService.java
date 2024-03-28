package com.ssafy.ibalance.diary.service;

import com.ssafy.ibalance.diary.dto.response.CalendarResponse;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import com.ssafy.ibalance.diet.repository.diet.DietRepository;
import com.ssafy.ibalance.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DietRepository dietRepository;

    public List<CalendarResponse> getCalendarList(Integer childId, int year, int month, Member member) {
        return dietRepository.getCalendarList(childId, year, month, member);
    }

    public List<DietByDateResponse> getDietByDate(Integer childId, LocalDate date, Member member) {
        return dietRepository.getDietByDate(childId, date, member);
    }
}
