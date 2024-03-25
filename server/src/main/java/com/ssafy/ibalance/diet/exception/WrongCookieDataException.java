package com.ssafy.ibalance.diet.exception;

import com.ssafy.ibalance.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class WrongCookieDataException extends CustomException {

    public WrongCookieDataException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
