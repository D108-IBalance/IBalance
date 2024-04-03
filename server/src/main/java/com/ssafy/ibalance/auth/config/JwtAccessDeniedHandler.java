package com.ssafy.ibalance.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ibalance.auth.type.JwtCode;
import com.ssafy.ibalance.common.dto.response.CommonWrapperResponse;
import com.ssafy.ibalance.common.type.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        String token = jwtTokenProvider.resolveToken(request);
        JwtCode jwtCode = jwtTokenProvider.validateToken(token);

        response.setContentType("application/json");
        int status = getStatusInfo(jwtCode);

        response.setStatus(status);
        response.getWriter().write(objectMapper.writeValueAsString(getErrorResponse(jwtCode, status)));
    }

    private int getStatusInfo(JwtCode jwtCode) {
        if(jwtCode == JwtCode.ACCESS) {
            return HttpStatus.FORBIDDEN.value();
        }
        return HttpStatus.UNAUTHORIZED.value();
    }

    private static CommonWrapperResponse getErrorResponse(JwtCode jwtCode, int status) {
        ErrorResponse errorResponse = switch (jwtCode) {
            case ACCESS -> errorResponseBuilder("AccessDeniedException", "권한이 없습니다.");
            case EXPIRED -> errorResponseBuilder("TokenExpieredException", "토큰이 만료되었습니다.");
            case DENIED -> errorResponseBuilder("TokenInvalidException", "토큰이 유효하지 않습니다.");
        };

        return CommonWrapperResponse.builder()
                .status(status)
                .data(errorResponse)
                .build();
    }

    private static ErrorResponse errorResponseBuilder(String errorType, String errorMessage) {
        return ErrorResponse.builder()
                .errorType(errorType)
                .message(errorMessage)
                .fieldName("")
                .build();
    }
}
