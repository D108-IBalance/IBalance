package com.ssafy.ibalance.member.dto.request;

import lombok.Builder;

public record LoginRequest(
        String code
) {
    @Builder
    public LoginRequest {
    }
}
