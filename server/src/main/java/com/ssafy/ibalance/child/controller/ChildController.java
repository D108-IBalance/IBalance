package com.ssafy.ibalance.child.controller;

import com.ssafy.ibalance.child.dto.request.RegistChildRequest;
import com.ssafy.ibalance.child.dto.response.ChildListResponse;
import com.ssafy.ibalance.child.dto.response.DeleteChildResponse;
import com.ssafy.ibalance.child.dto.response.GrowthResponse;
import com.ssafy.ibalance.child.dto.response.RegistChildResponse;
import com.ssafy.ibalance.child.service.ChildService;
import com.ssafy.ibalance.child.dto.response.ChildDietResponse;
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
    @GetMapping("/{childId}")
    public ChildDietResponse getMain(@PathVariable Integer childId,
                                     @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return childService.getMain(childId, date);
    }

    /**
     * 성장 데이터 조회
     *
     * @param childId 자녀 아이디
     * @param pageable ?page=0&size=5
     * @return 자녀의 키, 몸무게와 기록 날짜, 기록 날짜의 일요일과 토요일 날짜
     */
    @GetMapping("/growth/{childId}")
    public Page<GrowthResponse> getGrowthList(@PathVariable Integer childId, Pageable pageable) {
        return childService.getGrowthList(childId, pageable);
    }

}
