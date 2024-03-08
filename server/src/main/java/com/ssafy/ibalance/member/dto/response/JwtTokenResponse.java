package com.ssafy.ibalance.member.dto.response;

import com.ssafy.ibalance.member.type.OAuthProvider;
import lombok.Builder;

@Builder
public record JwtTokenResponse(String accessToken, String tokenType, OAuthProvider provider) {
}
