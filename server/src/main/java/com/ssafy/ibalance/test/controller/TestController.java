package com.ssafy.ibalance.test.controller;

import com.ssafy.ibalance.child.exception.ChildNotFoundException;
import com.ssafy.ibalance.common.dto.response.StringWrapper;
import com.ssafy.ibalance.member.entity.Member;
import com.ssafy.ibalance.test.dto.request.TestSaveRequest;
import com.ssafy.ibalance.test.entity.TesterEntity;
import com.ssafy.ibalance.test.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    @GetMapping("/hello/{name}")
    public StringWrapper hello(@PathVariable String name) {
        return StringWrapper.wrap("Hello " + name);
    }

    @GetMapping("/jpa/{address}")
    public List<TesterEntity> jpaTest(@PathVariable String address) {
        return testService.getTestUsingAddress(address);
    }

    @GetMapping("/querydsl/{name}")
    public List<TesterEntity> queryDslTest(@PathVariable String name) {
        return testService.getTestUsingName(name);
    }

    @GetMapping("/exceptional")
    public void exceptionHandlerTest() {
        throw new ChildNotFoundException("한번 아무 것으로 테스트 해 봅시다.");
    }

    @GetMapping("/login")
    public Member loginTest(@AuthenticationPrincipal Member member) {
        return member;
    }

    @PostMapping("/save")
    public void testSave(@RequestBody TestSaveRequest request) {
        testService.saveTestEntity(request);
    }
}
