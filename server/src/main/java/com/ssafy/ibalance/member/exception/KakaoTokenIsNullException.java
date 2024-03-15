package com.ssafy.ibalance.member.exception;

import com.ssafy.ibalance.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class KakaoTokenIsNullException extends CustomException {

    public KakaoTokenIsNullException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
