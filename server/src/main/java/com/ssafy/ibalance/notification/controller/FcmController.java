package com.ssafy.ibalance.notification.controller;

import com.ssafy.ibalance.member.entity.Member;
import com.ssafy.ibalance.notification.dto.request.FcmRequest;
import com.ssafy.ibalance.notification.dto.response.FcmResponse;
import com.ssafy.ibalance.notification.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fcm")
@RequiredArgsConstructor
public class FcmController {

    private final FcmService fcmService;

    /**
     * FCM 기기 등록 토큰 레디스에 저장
     *
     * @param member 로그인 한 멤버
     * @param fcmRequest FCM 기기 등록 토큰
     * @return 저장된 멤버 아이디와 토큰
     */
    @PostMapping("")
    public FcmResponse saveFCMToken(@AuthenticationPrincipal Member member,
                                    @RequestBody FcmRequest fcmRequest) {
        return fcmService.saveFcmToken(member,fcmRequest.getToken());
    }

}
