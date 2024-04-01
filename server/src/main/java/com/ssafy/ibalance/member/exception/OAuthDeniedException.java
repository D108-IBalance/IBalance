package com.ssafy.ibalance.member.exception;

import com.ssafy.ibalance.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class OAuthDeniedException extends CustomException {

    public OAuthDeniedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
