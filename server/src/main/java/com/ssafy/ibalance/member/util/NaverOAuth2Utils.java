package com.ssafy.ibalance.member.util;

import com.ssafy.ibalance.member.dto.OAuthMemberInfo;
import com.ssafy.ibalance.member.dto.response.NaverMemberInfoResponse;
import com.ssafy.ibalance.member.dto.response.NaverTokenResponse;
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
public class NaverOAuth2Utils {

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    @Value("${naver.token-uri}")
    private String tokenUri;

    @Value("${naver.user-info-uri}")
    private String userInfoUri;

    @Value("${naver.redirect-uri}")
    private String redirectUri;

    public OAuthMemberInfo getUserInfo(String code, String redirect){

        if(redirect == null){
            redirect = redirectUri;
        }

        String accessToken = getAccessToken(code, redirect);
        String bearerToken = "Bearer " + accessToken;

        return WebClient.create()
                .get()
                .uri(userInfoUri)
                .header("authorization", bearerToken)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(NaverMemberInfoResponse.class)
                .onErrorMap(e -> new OAuthDeniedException("code 또는 redirectUri 가 유효하지 않습니다."))
                .block();
    }

    private String getAccessToken(String code, String redirect){

        NaverTokenResponse tokenResponse = WebClient.create()
                .post()
                .uri(tokenUri)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .bodyValue(makeNaverTokenRequest(code, redirect))
                .retrieve()
                .bodyToMono(NaverTokenResponse.class)
                .block();

        if(tokenResponse != null){
            return tokenResponse.accessToken();
        }

        throw new OAuthInfoNullException("해당하는 유저가 없습니다.");
    }

    private MultiValueMap<String, String>  makeNaverTokenRequest(String code, String redirectUri){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);

        return params;
    }

}
