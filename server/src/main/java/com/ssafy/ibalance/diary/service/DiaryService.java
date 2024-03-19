package com.ssafy.ibalance.diary.service;

import com.ssafy.ibalance.diary.dto.response.CalendarResponse;
import com.ssafy.ibalance.diet.repository.DietRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DietRepository dietRepository;

    public List<CalendarResponse> getCalendarList(Integer childId, int year, int month) {
        return dietRepository.getCalendarList(childId, year, month);
    }
}
