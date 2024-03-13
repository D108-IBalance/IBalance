package com.ssafy.ibalance.main.controller;

import com.ssafy.ibalance.child.dto.response.GrowthResponse;
import com.ssafy.ibalance.main.dto.response.MainPageResponse;
import com.ssafy.ibalance.main.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 김주이
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainPageController {

    private final MainPageService mainService;

    /**
     * 메인 페이지에서 자녀 정보와 오늘의 식단 조회
     *
     * @param childId
     * @return
     */
    @GetMapping("/{childId}")
    public MainPageResponse getMain(@PathVariable Integer childId,
                                    @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return mainService.getMain(childId, date);
    }

    @GetMapping("/{childId}/growth")
    public List<GrowthResponse> getMainGrowth(@PathVariable Integer childId) {
        return mainService.getGrowth(childId);
    }

}
