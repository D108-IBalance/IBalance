package com.ssafy.ibalance.member.service;

import com.ssafy.ibalance.member.dto.response.KakaoMemberInfoResponse;
import com.ssafy.ibalance.member.dto.response.KakaoTokenResponse;
import com.ssafy.ibalance.member.entity.Member;
import com.ssafy.ibalance.member.exception.OAuthInfoNullException;
import com.ssafy.ibalance.member.exception.KakaoTokenIsNullException;
import com.ssafy.ibalance.member.repository.MemberRepository;
import com.ssafy.ibalance.member.type.OAuthProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class OauthService {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.info-url}")
    private String kakaoInfoUrl;

    @Value("${kakao.token-url}")
    private String kakaoTokenUrl;

    private final MemberRepository memberRepository;

    public Member getKakaoInfo(String code) {
        log.info("getKakaoInfo 호출 : {}", code);

        String kakaoAccessToken = getKakaoToken(code);

        log.info("유저 정보 가져오기 : {}", kakaoAccessToken);

        WebClient webClient = WebClient.builder()
                .baseUrl(kakaoInfoUrl)
                .defaultHeader("Authorization", "Bearer " + kakaoAccessToken)
                .defaultHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .build();

        KakaoMemberInfoResponse kakaoUserInfoResponse = webClient.get()
                .retrieve()
                .bodyToMono(KakaoMemberInfoResponse.class)
                .block();

        // kakao info null 확인
        if(kakaoUserInfoResponse == null) {
            throw new OAuthInfoNullException("해당하는 유저가 없습니다.");
        }

        return memberRepository.findByCodeAndProvider(kakaoUserInfoResponse.getId().toString(), OAuthProvider.KAKAO)
                .orElseGet(() -> memberRepository.save(Member.builder()
                .code(kakaoUserInfoResponse.getId().toString())
                .provider(OAuthProvider.KAKAO)
                .build()));
    }

    public String getKakaoToken(String code) throws KakaoTokenIsNullException {
        log.info("getKakaoToken 호출 : {}", code);

        WebClient webClient = WebClient.builder()
                .baseUrl(kakaoTokenUrl)
                .defaultHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .build();

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", clientId);
        requestBody.add("redirect_uri", redirectUri);
        requestBody.add("code", code);

        KakaoTokenResponse kakaoTokenResponse = webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(KakaoTokenResponse.class)
                .block();

        log.info("kakaoTokenResponseDto : {}", kakaoTokenResponse);

        if(kakaoTokenResponse != null) {
            return kakaoTokenResponse.getAccessToken();
        }

        throw new KakaoTokenIsNullException("kakao accessToken이 없습니다.");
    }
}
