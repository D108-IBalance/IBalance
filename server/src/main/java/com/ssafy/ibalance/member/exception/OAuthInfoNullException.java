package com.ssafy.ibalance.member.exception;

import com.ssafy.ibalance.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class OAuthInfoNullException extends CustomException {
    public OAuthInfoNullException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
