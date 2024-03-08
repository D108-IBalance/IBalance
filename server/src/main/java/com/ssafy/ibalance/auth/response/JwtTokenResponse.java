package com.ssafy.ibalance.auth.response;

import com.ssafy.ibalance.member.type.OAuthProvider;
import lombok.Builder;

public record JwtTokenResponse(String accessToken, String tokenType, OAuthProvider oAuthProvider) {
    @Builder
    public JwtTokenResponse {

    }
}
