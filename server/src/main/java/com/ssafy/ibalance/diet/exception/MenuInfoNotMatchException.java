package com.ssafy.ibalance.diet.exception;

import com.ssafy.ibalance.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class MenuInfoNotMatchException extends CustomException {
    public MenuInfoNotMatchException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
