package com.ssafy.ibalance.member.type;

public enum OAuthProvider {
    GOOGLE, KAKAO, NAVER;

    public static OAuthProvider getOAuthProvider(String inputProvider){
        return switch (inputProvider){
            case "kakao" -> KAKAO;
            case "naver" -> NAVER;
            case "google" -> GOOGLE;
            default -> throw new IllegalArgumentException("잘못된 요청입니다."); // TODO : Custom Exception & Exception Handler 처리할 것
        };
    }
}
