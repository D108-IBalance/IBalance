package com.ssafy.ibalance.member.util;

import com.ssafy.ibalance.member.dto.response.KakaoMemberInfoResponse;
import com.ssafy.ibalance.member.dto.response.KakaoTokenResponse;
import com.ssafy.ibalance.member.exception.KakaoTokenIsNullException;
import com.ssafy.ibalance.member.exception.OAuthDeniedException;
import com.ssafy.ibalance.member.exception.OAuthInfoNullException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoOAuth2Utils {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.info-url}")
    private String kakaoInfoUrl;

    @Value("${kakao.token-url}")
    private String kakaoTokenUrl;

    public KakaoMemberInfoResponse getUserInfo(String code, String redirect) {
        log.info("getKakaoInfo 호출 : {}", code);

        if(redirect == null){
            redirect = redirectUri;
        }

        String kakaoAccessToken = getKakaoToken(code, redirect);

        log.info("유저 정보 가져오기 : {}", kakaoAccessToken);

        WebClient webClient = WebClient.builder()
                .baseUrl(kakaoInfoUrl)
                .defaultHeader("Authorization", "Bearer " + kakaoAccessToken)
                .defaultHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .build();

        return webClient.get()
                .retrieve()
                .bodyToMono(KakaoMemberInfoResponse.class)
                .block();
    }

    public String getKakaoToken(String code, String redirect) throws KakaoTokenIsNullException {
        log.info("getKakaoToken 호출 : {}", code);

        WebClient webClient = WebClient.builder()
                .baseUrl(kakaoTokenUrl)
                .defaultHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .build();

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", clientId);
        requestBody.add("redirect_uri", redirect);
        requestBody.add("code", code);

        KakaoTokenResponse kakaoTokenResponse = webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(KakaoTokenResponse.class)
                .onErrorMap(e -> new OAuthDeniedException("code 또는 redirectUri 가 유효하지 않습니다."))
                .block();

        log.info("kakaoTokenResponseDto : {}", kakaoTokenResponse);

        if(kakaoTokenResponse != null) {
            return kakaoTokenResponse.getAccessToken();
        }

        throw new OAuthInfoNullException("해당하는 유저가 없습니다.");
    }
}
