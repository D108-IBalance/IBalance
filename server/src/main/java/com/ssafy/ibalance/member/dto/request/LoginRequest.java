package com.ssafy.ibalance.member.dto.request;

import lombok.Builder;

@Builder
public record LoginRequest(
        String code
) {
}
