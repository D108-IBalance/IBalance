package com.ssafy.ibalance.member.util;

import com.ssafy.ibalance.member.dto.response.GoogleMemberInfoResponse;
import com.ssafy.ibalance.member.dto.response.GoogleTokenResponse;
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

    public GoogleMemberInfoResponse getUserInfo(String code, String redirect) {

        if(redirect == null){
            redirect = redirectUri;
        }

        String accessToken = getAccessToken(code, redirect);
        String bearerToken = "Bearer " + accessToken;

        return WebClient.create()
                .get()
                .uri(userInfoUri)
                .header("authorization", bearerToken)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(GoogleMemberInfoResponse.class)
                .block();
    }

    private String getAccessToken(String code, String redirectUri){
        GoogleTokenResponse accessTokenAnswer = WebClient.create()
                .post()
                .uri(tokenUri)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .bodyValue(makeGoogleTokenRequest(code, redirectUri))
                .retrieve()
                .bodyToMono(GoogleTokenResponse.class)
                .onErrorMap(e -> new OAuthDeniedException("code 또는 redirectUri 가 유효하지 않습니다."))
                .block();

        if(accessTokenAnswer != null){
            return accessTokenAnswer.accessToken();
        }

        throw new OAuthInfoNullException("해당하는 유저가 없습니다.");
    }


    private MultiValueMap<String, String> makeGoogleTokenRequest(String code, String redirectUri){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        return params;
    }
}
