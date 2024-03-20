package com.ssafy.ibalance.diary.service;

import com.ssafy.ibalance.diary.dto.response.CalendarResponse;
import com.ssafy.ibalance.diet.repository.DietRepository;
import com.ssafy.ibalance.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryService {
    private final DietRepository dietRepository;

    public List<CalendarResponse> getCalendarList(Integer childId, int year, int month, Member member) {
        return dietRepository.getCalendarList(childId, year, month, member);
    }
}
