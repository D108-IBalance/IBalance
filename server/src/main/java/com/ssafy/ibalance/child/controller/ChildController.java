package com.ssafy.ibalance.child.controller;

import com.ssafy.ibalance.child.dto.request.RegistChildRequest;
import com.ssafy.ibalance.child.dto.response.*;
import com.ssafy.ibalance.child.service.ChildService;
import com.ssafy.ibalance.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 박성진, 김주이
 */
@RestController
@RequestMapping("/child")
@RequiredArgsConstructor
public class ChildController {

    private final ChildService childService;

    @GetMapping("")
    public List<ChildListResponse> getChildList(@AuthenticationPrincipal Member member) {

        return childService.getChildList(member.getId());
    }

    @PostMapping("")
    public RegistChildResponse registChild(@RequestBody RegistChildRequest registChildRequest,
                                           @AuthenticationPrincipal Member member) {

        return childService.registChild(registChildRequest, member);
    }

    @DeleteMapping("/{childId}")
    public DeleteChildResponse deleteChild(@PathVariable Integer childId) {

        return childService.deleteChild(childId);
    }

    /**
     * 메인 페이지에서 자녀 정보와 오늘의 식단 조회
     *
     * @param childId
     * @return
     */
    @GetMapping("/main/{childId}")
    public ChildDietResponse getMain(@PathVariable Integer childId,
                                     @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                     @AuthenticationPrincipal Member member) {
        return childService.getMain(childId, date, member);
    }

    /**
     * 성장 데이터 조회
     *
     * @param childId 자녀 아이디
     * @param pageable ?page=0&size=5
     * @return 마지막 페이지 여부,
     *         자녀 성장 데이터(자녀의 키, 몸무게와 기록 날짜, 기록 날짜의 일요일과 토요일 날짜) 리스트,
     *         평균 성장 데이터 리스트
     */
    @GetMapping("/growth/{childId}")
    public GrowthPageResponse getGrowthList(@PathVariable Integer childId, Pageable pageable,
                                            @AuthenticationPrincipal Member member) {
        return childService.getGrowthList(childId, pageable, member);
    }

}
