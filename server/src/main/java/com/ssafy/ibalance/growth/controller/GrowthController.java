package com.ssafy.ibalance.growth.controller;

import com.ssafy.ibalance.growth.dto.response.GrowthResponse;
import com.ssafy.ibalance.growth.service.GrowthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 김주이
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/growth")
public class GrowthController {

    private final GrowthService growthService;

    /**
     * 성장 그래프 페이지에서 필요한 데이터 조회
     *
     * @param childId 자녀 아이디
     * @param pageable ?page=0&size=5
     * @return 자녀의 키, 몸무게와 기록 날짜, 기록 날짜의 일요일과 토요일 날짜
     */
    @GetMapping("/{childId}")
    public List<GrowthResponse> getGrowthList(@PathVariable Integer childId, Pageable pageable) {
        return growthService.getGrowthList(childId, pageable);
    }

}
