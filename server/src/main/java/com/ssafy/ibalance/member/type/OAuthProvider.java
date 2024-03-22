package com.ssafy.ibalance.member.type;

import com.ssafy.ibalance.member.exception.ProviderNotSupportedException;

public enum OAuthProvider {
    GOOGLE, KAKAO, NAVER;

    public static OAuthProvider getOAuthProvider(String inputProvider) {

        String convertProvider = inputProvider.toUpperCase();

        return switch (convertProvider) {
            case "KAKAO" -> KAKAO;
            case "NAVER" -> NAVER;
            case "GOOGLE" -> GOOGLE;
            default -> throw new ProviderNotSupportedException("잘못된 요청입니다.");
        };
    }
}
