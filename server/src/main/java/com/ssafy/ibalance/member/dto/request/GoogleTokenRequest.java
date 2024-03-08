package com.ssafy.ibalance.member.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record GoogleTokenRequest(
        @JsonProperty("grant_type")
        String grantType,
        String code,
        @JsonProperty("client_id")
        String clientId,

        @JsonProperty("client_secret")
        String clientSecret,

        @JsonProperty("redirect_url")
        String redirectUri
) {
}
