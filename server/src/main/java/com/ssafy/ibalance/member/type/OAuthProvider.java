package com.ssafy.ibalance.member.type;

public enum OAuthProvider {
    GOOGLE, KAKAO, NAVER;

    public static OAuthProvider getOAuthProvider(String inputProvider){

        String convertProvider = inputProvider.toUpperCase();

        return switch (convertProvider){
            case "KAKAO" -> KAKAO;
            case "NAVER" -> NAVER;
            case "GOOGLE" -> GOOGLE;
            default -> throw new IllegalArgumentException("잘못된 요청입니다."); // TODO : Custom Exception & Exception Handler 처리할 것
        };
    }
}
