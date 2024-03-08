package com.ssafy.ibalance.common.hadler;

import com.ssafy.ibalance.common.type.ErrorResponse;

import java.util.List;

public class ExceptionHandlerTool {
    public static List<ErrorResponse> makeErrorResponse(Exception e, String fieldName) {
        return List.of(ErrorResponse.builder()
                .message(e.getMessage())
                .errorType(e.getClass().getSimpleName())
                .fieldName(fieldName)
                .build());
    }
}
