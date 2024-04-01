package com.ssafy.ibalance.test.controller;

import com.ssafy.ibalance.child.exception.ChildAccessDeniedException;
import com.ssafy.ibalance.test.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Value("${JASYPT_KEY}")
    private String jasyptKey;

    @PostMapping("/refresh-allergy/{password}")
    public void refreshAllergy(@PathVariable String password) {
        if(!password.equals(jasyptKey)) {
            throw new ChildAccessDeniedException("암호가 잘못되었습니다.");
        }

        adminService.refreshAllChildAllergy();
    }
}
