package com.ssafy.ibalance.member.handler;

import com.ssafy.ibalance.common.type.ErrorResponse;
import com.ssafy.ibalance.member.exception.OAuthInfoNullException;
import com.ssafy.ibalance.member.exception.KakaoTokenIsNullException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.ssafy.ibalance.common.hadler.ExceptionHandlerTool.makeErrorResponse;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(KakaoTokenIsNullException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List<ErrorResponse> kakaoTokenExceptionHandler(KakaoTokenIsNullException e) {
        return makeErrorResponse(e, "code");
    }

    @ExceptionHandler(OAuthInfoNullException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List<ErrorResponse> kakaoInfoExceptionHandler(OAuthInfoNullException e) {
        return makeErrorResponse(e, "code");
    }
}
