package com.ssafy.ibalance.member.controller;

import com.ssafy.ibalance.member.entity.Member;
import com.ssafy.ibalance.member.service.OauthService;
import com.ssafy.ibalance.member.type.OAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author 김주이
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OauthController {

    private final OauthService oauthService;

    /**
     * 소셜 로그인 (KAKAO, GOOGLE, NAVER) 후 회원 정보를 반환
     *
     * @author 김주이
     * @param oAuthProvider oauth 제공자
     * @param code ouath에서 반환하는 인가 코드
     * @return 로그인 한 회원 정보
     */
    @PostMapping("/login/{provider}")
    public Member socialLogin(@PathVariable("provider") OAuthProvider oAuthProvider, @RequestBody String code) {
        switch (oAuthProvider) {
            case KAKAO -> {
               return oauthService.getKakaoInfo(code);
            }
        }

        return null;
    }
}
