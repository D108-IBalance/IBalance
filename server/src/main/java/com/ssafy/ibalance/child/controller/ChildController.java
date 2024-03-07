package com.ssafy.ibalance.child.controller;

import com.ssafy.ibalance.child.dto.request.RegistChildRequestDto;
import com.ssafy.ibalance.child.dto.response.ChildListResponseDto;
import com.ssafy.ibalance.child.service.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public void registChild(@RequestBody RegistChildRequestDto registChildRequestDto) {

        childService.registChild(registChildRequestDto);
    }

}
