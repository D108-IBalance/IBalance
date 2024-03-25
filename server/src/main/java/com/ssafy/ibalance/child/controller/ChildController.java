package com.ssafy.ibalance.child.controller;

import com.ssafy.ibalance.child.dto.request.RegistChildRequest;
import com.ssafy.ibalance.child.dto.response.*;
import com.ssafy.ibalance.child.service.ChildService;
import com.ssafy.ibalance.member.entity.Member;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 박성진, 김주이
 */
@RestController
@RequestMapping("/child")
@RequiredArgsConstructor
@Validated
public class ChildController {

    private final ChildService childService;

    /**
     * 자녀 목록 조회
     *
     * @param member 로그인 한 멤버
     * @return 자녀 목록 (자녀 아이디, 이름, 프로필 이미지 url, 성별)
     */
    @GetMapping("")
    public List<ChildInfoResponse> getChildList(@AuthenticationPrincipal Member member) {

        return childService.getChildList(member.getId());
    }

    /**
     * 자녀 등록
     *
     * @param registChildRequest 자녀 이름, 생년월일, 성별, 키, 몸무게, 알러지
     * @param member 로그인 한 멤버
     * @return 등록된 자녀 정보 (자녀 아이디, 이름, 생년월일, 성별, 키, 몸무게, 프로필 이미지 url, 멤버 아이디)
     */
    @PostMapping("")
    public RegistChildResponse registChild(@Valid @RequestBody RegistChildRequest registChildRequest,
                                           @AuthenticationPrincipal Member member) {

        return childService.registChild(registChildRequest, member);
    }

    /**
     * 자녀 삭제
     *
     * @param member 로그인 한 멤버
     * @param childId 자녀 아이디
     * @return 삭제된 자녀 정보 (자녀 아이디, 이름, 멤버 아이디)
     */
    @DeleteMapping("/{childId}")
    public DeleteChildResponse deleteChild(@AuthenticationPrincipal Member member,
                                           @PathVariable @Min(value = 1, message = "자녀 아이디는 1 이상이어야 합니다.") Integer childId) {

        return childService.deleteChild(member, childId);
    }

    /**
     * 메인 페이지에서 자녀 정보와 오늘의 식단 조회
     *
     * @param childId 자녀 아이디
     * @param date 오늘 날짜 (yyyy-MM-dd)
     * @param member 로그인 한 멤버
     * @return 자녀 정보 (자녀 아이디, 프로필 이미지 url, 이름, 생년월일, 성별, 키, 몸무게, 마지막 업데이트 날짜)와
     *         오늘의 식단 (식단 아이디, 식단 날짜, 순서, 메뉴 목록)
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
     * @param pageable 현재 조회하고 있는 페이지. page, size=4
     * @param member 로그인 한 멤버
     * @return 자녀의 성장 데이터 (성별, 생년월일, 개월 수, 기록일, 기록일 기준 일주일의 시작일과 종료일, 키, 몸무게)와
     *         평균 데이터 (개월 수, 평균 키, 평균 몸무게)
     */
    @GetMapping("/growth/{childId}")
    public GrowthPageResponse getGrowthList(@PathVariable Integer childId, Pageable pageable,
                                            @AuthenticationPrincipal Member member) {
        return childService.getGrowthList(childId, pageable, member);
    }
}
