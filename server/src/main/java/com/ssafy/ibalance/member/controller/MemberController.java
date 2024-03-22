package com.ssafy.ibalance.member.controller;

import com.ssafy.ibalance.auth.config.JwtTokenProvider;
import com.ssafy.ibalance.auth.response.JwtTokenResponse;
import com.ssafy.ibalance.member.dto.request.LoginRequest;
import com.ssafy.ibalance.member.entity.Member;
import com.ssafy.ibalance.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author 남동우, 김주이
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 실제 회원 소셜 로그인
     *
     * @param code Oauth에서 보내주는 인가 코드
     * @param provider Oauth Provider (kakao, google, naver)
     * @param response 쿠키 저장을 위한 response
     * @return 로그인 한 회원 JWT token 정보
     */
    @PostMapping(value = "/login/{provider}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public JwtTokenResponse login(@RequestBody LoginRequest request, @PathVariable String provider, HttpServletResponse response) {
        Member memberInfo = memberService.getMemberInfo(provider, request.getCode(), request.getUrl());

        jwtTokenProvider.setRefreshTokenForClient(response, memberInfo);
        return jwtTokenProvider.makeJwtTokenResponse(memberInfo);
    }

    /**
     * Oauth provider redirect url 테스트용
     *
     * @param code Oauth에서 보내주는 인가 코드
     * @param provider Oauth Provider (kakao, google, naver)
     * @param response 쿠키 저장을 위한 response
     * @return 로그인 한 회원 JWT token 정보
     */
    @GetMapping("/login/{provider}")
    public JwtTokenResponse testLogin(@RequestParam String code,
                                      @RequestParam(required = false) String redirectUri,
                                      @PathVariable String provider, HttpServletResponse response){

        Member memberInfo = memberService.getMemberInfo(provider, code, redirectUri);

        jwtTokenProvider.setRefreshTokenForClient(response, memberInfo);
        return jwtTokenProvider.makeJwtTokenResponse(memberInfo);
    }

    /**
     * 회원 로그아웃
     *
     * @param response 쿠키 삭제를 위한 response
     */
    @GetMapping("/logout")
    public void logout(HttpServletResponse response) {
        jwtTokenProvider.removeRefreshTokenForClient(response);
    }
}
