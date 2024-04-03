package com.ssafy.ibalance.diet.exception;

import com.ssafy.ibalance.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NoMoreExistMenuException extends CustomException {
    public NoMoreExistMenuException(String message) {
        super(message, HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
    }
}
