package com.ssafy.ibalance.common.type;

import lombok.Builder;

public record ErrorResponse(String errorType, String message, String fieldName) {
    @Builder
    public ErrorResponse {

    }
}
