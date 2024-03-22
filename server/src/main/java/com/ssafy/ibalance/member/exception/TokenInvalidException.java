package com.ssafy.ibalance.member.exception;

import com.ssafy.ibalance.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class TokenInvalidException extends CustomException {

    public TokenInvalidException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
