package com.ssafy.ibalance.member.util;

import com.ssafy.ibalance.member.dto.response.GoogleMemberInfoResponse;
import com.ssafy.ibalance.member.dto.response.GoogleTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:oauth.properties")
public class GoogleOAuth2Utils {

    @Value("${google.user-info-url}")
    private String userInfoUri;

    @Value("${google.token-uri}")
    private String tokenUri;

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    @Value("${google.client-secret}")
    private String clientSecret;

    private static final String USER_INFO_ENDPOINT = "https://www.googleapis.com/oauth2/v3/userinfo";

    public GoogleMemberInfoResponse getUserInfo(String code) {

        // 1. 먼저, WebClient 로 유저정보를 가져올 수 있는지 테스트한다.
        // 2. 그런 다음에, authorization 에 어떤 내용이 들어가 있는지 파악한다
        // 3. 그에 맞게, GoogleUserInfo 내용을 생성해 준다
        // 4. 이
        String accessToken = getAccessToken(code);
        String bearerToken = "Bearer " + accessToken;

        return WebClient.create()
                .get()
                .uri(USER_INFO_ENDPOINT)
                .header("authorization", bearerToken)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(GoogleMemberInfoResponse.class)
                .block();
    }

    private String getAccessToken(String code){
        GoogleTokenResponse accessTokenAnswer = WebClient.create()
                .post()
                .uri(tokenUri)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .bodyValue(makeGoogleTokenRequest(code))
                .retrieve()
                .bodyToMono(GoogleTokenResponse.class)
                .block();

        if(accessTokenAnswer != null){
            return accessTokenAnswer.accessToken();
        }

        return null; // TODO : Custom Exception & Exception Handler 처리하기
    }


    private MultiValueMap<String, String> makeGoogleTokenRequest(String code){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        return params;
    }
}
