package com.ssafy.ibalance.diary.controller;

import com.ssafy.ibalance.diary.dto.response.CalendarResponse;
import com.ssafy.ibalance.diary.service.DiaryService;
import com.ssafy.ibalance.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @GetMapping("/calendar/{childId}")
    public List<CalendarResponse> getCalenderList(@PathVariable Integer childId,
                                                  @RequestParam int year, @RequestParam int month,
                                                  @AuthenticationPrincipal Member member) {
        return diaryService.getCalendarList(childId, year, month, member);
    }

}
