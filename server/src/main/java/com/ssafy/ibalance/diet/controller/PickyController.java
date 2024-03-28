package com.ssafy.ibalance.diet.controller;

import com.ssafy.ibalance.diet.dto.response.PickyResultResponse;
import com.ssafy.ibalance.diet.service.PickyService;
import com.ssafy.ibalance.diet.type.PeriodUnit;
import com.ssafy.ibalance.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/picky")
@RequiredArgsConstructor
public class PickyController {

    private final PickyService pickyService;

    @GetMapping("/{childId}")
    public PickyResultResponse getPickyOfChild(@AuthenticationPrincipal Member member,
                                               @PathVariable Integer childId,
                                               @RequestParam String limit) {

        return pickyService.getPickyResult(member, childId, PeriodUnit.valueOf(limit));
    }
}
