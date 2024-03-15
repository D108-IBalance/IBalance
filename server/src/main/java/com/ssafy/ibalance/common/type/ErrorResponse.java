package com.ssafy.ibalance.common.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import org.springframework.http.HttpStatus;

public record ErrorResponse(String errorType,
                            String message,
                            String fieldName,
                            @JsonIgnore
                            HttpStatus status) {
    @Builder
    public ErrorResponse {

    }
}
