package com.ssafy.ibalance.test.controller;

import com.ssafy.ibalance.test.dto.request.TestSaveRequest;
import com.ssafy.ibalance.test.entity.TesterEntity;
import com.ssafy.ibalance.test.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name){
        return "hello " + name;
    }

    @GetMapping("/jpa/{address}")
    public List<TesterEntity> jpaTest(@PathVariable String address){
        return testService.getTestUsingAddress(address);
    }

    @GetMapping("/querydsl/{name}")
    public List<TesterEntity> queryDslTest(@PathVariable String name){
        return testService.getTestUsingName(name);
    }

    @PostMapping("/save")
    public void testSave(@RequestBody TestSaveRequest request){
        testService.saveTestEntity(request);
    }
}
