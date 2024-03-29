package com.ssafy.ibalance.diary.controller;

import com.ssafy.ibalance.common.dto.annotation.DatePattern;
import com.ssafy.ibalance.diary.dto.annotation.CheckMonth;
import com.ssafy.ibalance.diary.dto.response.CalendarResponse;
import com.ssafy.ibalance.diary.dto.response.DiaryInfoResponse;
import com.ssafy.ibalance.diary.service.DiaryService;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import com.ssafy.ibalance.member.entity.Member;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author 김주이
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
@Validated
public class DiaryController {

    private final DiaryService diaryService;

    /**
     * 해당 년, 월에 식단이 존재하는 날짜 목록 조회
     *
     * @param childId 자녀 아이디
     * @param year    조회하려는 연도
     * @param month   조회하려는 월
     * @param member  로그인 한 멤버
     * @return 식단이 존재하는 날짜와 그 날짜의 모든 식단의 리뷰 여부
     */
    @GetMapping("/calendar/{childId}")
    public List<CalendarResponse> getCalenderList(@PathVariable @Min(value = 1, message = "자녀 ID 는 1 이상이어야 합니다.") Integer childId,
                                                  @RequestParam int year,
                                                  @RequestParam @CheckMonth int month,
                                                  @AuthenticationPrincipal Member member) {
        return diaryService.getCalendarList(childId, year, month, member);
    }

    /**
     * 선택한 날짜의 식단 목록 조회
     *
     * @param childId 자녀 아이디
     * @param date    선택한 날짜 (yyyy-MM-dd)
     * @param member  로그인 한 멤버
     * @return 식단 목록 (식단 아이디, 식단 날짜, 순서, 메뉴 목록)
     */
    @GetMapping("/{childId}")
    public List<DietByDateResponse> getDietByDate(@PathVariable @Min(value = 1, message = "자녀 ID 는 1 이상이어야 합니다.") Integer childId,
                                                  @RequestParam @DatePattern String date,
                                                  @AuthenticationPrincipal Member member) {
        return diaryService.getDietByDate(childId, LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")), member);
    }

    /**
     * 선택한 날짜의 식단 목록 조회
     *
     * @param dietId 확인하고자 하는 상세 diet 의 ID
     * @param member 로그인 한 멤버
     * @return
     */
    @GetMapping("/detail/{dietId}")
    public DiaryInfoResponse getDiaryWriteInfo(@AuthenticationPrincipal Member member,
                                               @PathVariable @Min(value = 1, message = "식단 아이디는 1 이상이어야 합니다.") Long dietId) {

        return diaryService.getDiaryWriteInfo(member, dietId);
    }

}
