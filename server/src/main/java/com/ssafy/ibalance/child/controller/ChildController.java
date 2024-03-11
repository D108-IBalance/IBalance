package com.ssafy.ibalance.child.controller;

import com.ssafy.ibalance.child.dto.request.RegistChildRequest;
import com.ssafy.ibalance.child.dto.response.ChildListResponse;
import com.ssafy.ibalance.child.dto.response.DeleteChildResponse;
import com.ssafy.ibalance.child.dto.response.RegistChildResponse;
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
    public List<ChildListResponse> getChildList() {

        Integer memberId = 1;   // 로그인 기능 완료 시 JWT 토큰에서 memberId 추출 필요
        return childService.getChildList(memberId);
    }

    @PostMapping
    public RegistChildResponse registChild(@RequestBody RegistChildRequest registChildRequest) {

        return childService.registChild(registChildRequest);
    }

    @DeleteMapping("/{childId}")
    public DeleteChildResponse deleteChild(@PathVariable Integer childId) {

        return childService.deleteChild(childId);
    }

}
