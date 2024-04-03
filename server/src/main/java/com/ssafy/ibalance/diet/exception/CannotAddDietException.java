package com.ssafy.ibalance.diet.exception;

import com.ssafy.ibalance.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CannotAddDietException extends CustomException {

    public CannotAddDietException(String message) {
        super(message, HttpStatus.NOT_ACCEPTABLE);
    }
}
