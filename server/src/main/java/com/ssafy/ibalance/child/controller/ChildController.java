package com.ssafy.ibalance.child.controller;

import com.ssafy.ibalance.child.dto.response.ChildListResponseDto;
import com.ssafy.ibalance.child.service.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/child")
@RequiredArgsConstructor
public class ChildController {

    private final ChildService childService;

    @GetMapping
    public List<ChildListResponseDto> getChildList() {

        Integer memberId = 1;   // 로그인 기능 완료 시 JWT 토큰에서 memberId 추출 필요
        return childService.getChildList(memberId);
    }

}
