package com.ssafy.ibalance.member.util;

import com.ssafy.ibalance.member.dto.response.KakaoMemberInfoResponse;
import com.ssafy.ibalance.member.dto.response.KakaoTokenResponseDto;
import com.ssafy.ibalance.member.exception.KakaoTokenIsNullException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@PropertySource("classpath:oauth.properties")
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

    public KakaoMemberInfoResponse getKakaoInfo(String code) {
        log.info("getKakaoInfo 호출 : {}", code);

        String kakaoAccessToken = getKakaoToken(code);

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

        KakaoTokenResponseDto kakaoTokenResponseDto = webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();

        log.info("kakaoTokenResponseDto : {}", kakaoTokenResponseDto);

        if(kakaoTokenResponseDto != null) {
            return kakaoTokenResponseDto.getAccessToken();
        }

        throw new KakaoTokenIsNullException("kakao accessToken이 없습니다.");
    }
}
