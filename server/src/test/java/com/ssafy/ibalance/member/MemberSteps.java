package com.ssafy.ibalance.member;

import com.ssafy.ibalance.common.MemberTestUtil;
import com.ssafy.ibalance.member.dto.request.LoginRequest;
import org.springframework.stereotype.Component;

@Component
public class MemberSteps {


    public LoginRequest 로그인_생성(String code){
        return LoginRequest.builder()
                .code(code)
                .url(MemberTestUtil.googleRedirectUrl)
                .build();
    }

    public LoginRequest 로그인_생성_카카오(String code){
        return LoginRequest.builder()
                .code(code)
                .url(MemberTestUtil.kakaoRedirectUrl)
                .build();
    }
}
