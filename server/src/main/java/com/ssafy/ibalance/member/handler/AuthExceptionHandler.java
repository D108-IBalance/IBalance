package com.ssafy.ibalance.member.handler;

import com.ssafy.ibalance.common.type.ErrorResponse;
import com.ssafy.ibalance.member.exception.OAuthDeniedException;
import com.ssafy.ibalance.member.exception.OAuthInfoNullException;
import com.ssafy.ibalance.member.exception.KakaoTokenIsNullException;
import com.ssafy.ibalance.member.exception.ProviderNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.ssafy.ibalance.common.hadler.ExceptionHandlerTool.makeErrorResponse;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(KakaoTokenIsNullException.class)
    public List<ErrorResponse> kakaoTokenExceptionHandler(KakaoTokenIsNullException e) {
        return makeErrorResponse(e, "code");
    }

    @ExceptionHandler(OAuthInfoNullException.class)
    public List<ErrorResponse> kakaoInfoExceptionHandler(OAuthInfoNullException e) {
        return makeErrorResponse(e, "code");
    }

    @ExceptionHandler(ProviderNotSupportedException.class)
    public List<ErrorResponse> providerNotSupportedExceptionHandler(ProviderNotSupportedException e) {
        return makeErrorResponse(e, "provider");
    }

    @ExceptionHandler(OAuthDeniedException.class)
    public List<ErrorResponse> oAuthDeniedExceptionHandler(OAuthDeniedException e) {
        return makeErrorResponse(e, "code");
    }
}
