package com.ssafy.ibalance.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ibalance.auth.type.JwtCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken(request);
        JwtCode jwtCode = jwtTokenProvider.validateToken(token);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, String> errorMsg = getErrorMessageMap(jwtCode);
        response.getWriter().write(objectMapper.writeValueAsString(errorMsg));
    }

    private static Map<String, String> getErrorMessageMap(JwtCode jwtCode) {
        Map<String, String> errorMsg = new LinkedHashMap<>();

        switch (jwtCode) {
            case ACCESS:
                errorMsg.put("message", "권한이 없습니다.");
                errorMsg.put("errorType", "AccessDeniedException");
            case EXPIRED:
                errorMsg.put("message", "토큰이 만료되었습니다.");
                errorMsg.put("errorType", "TokenExpiredException");
            case DENIED:
                errorMsg.put("message", "토큰이 유효하지 않습니다.");
                errorMsg.put("errorType", "TokenInvalidException");
        }

        errorMsg.put("fieldName", "");
        return errorMsg;
    }
}
