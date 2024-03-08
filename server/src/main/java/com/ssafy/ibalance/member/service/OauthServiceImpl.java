package com.ssafy.ibalance.member.service;

import com.ssafy.ibalance.member.dto.response.KakaoInfoResponseDto;
import com.ssafy.ibalance.member.dto.response.KakaoTokenResponseDto;
import com.ssafy.ibalance.member.exception.KakaoInfoIsNullException;
import com.ssafy.ibalance.member.exception.KakaoTokenIsNullException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class OauthServiceImpl implements OauthService {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Override
    public KakaoInfoResponseDto getKakaoInfo(String code) {
        log.info("getKakaoInfo 호출 : {}", code);

        String kakaoAccessToken = getKakaoToken(code);

        log.info("유저 정보 가져오기 : {}", kakaoAccessToken);

        String kakaoInfoUrl = "https://kapi.kakao.com/v2/user/me";

        WebClient webClient = WebClient.builder()
                .baseUrl(kakaoInfoUrl)
                .defaultHeader("Authorization", "Bearer " + kakaoAccessToken)
                .defaultHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .build();

        KakaoInfoResponseDto kakaoInfoResponseDto = webClient.get()
                .retrieve()
                .bodyToMono(KakaoInfoResponseDto.class)
                .block();

        if(kakaoInfoResponseDto != null) {
            return kakaoInfoResponseDto;
        }

        throw new KakaoInfoIsNullException("해당하는 유저가 없습니다.");
    }

    @Override
    public String getKakaoToken(String code) throws KakaoTokenIsNullException {
        log.info("getKakaoToken 호출 : {}", code);

        String kakaoTokenUrl = "https://kauth.kakao.com/oauth/token";

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
