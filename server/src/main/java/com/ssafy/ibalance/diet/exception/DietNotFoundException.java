package com.ssafy.ibalance.diet.exception;

import com.ssafy.ibalance.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class DietNotFoundException extends CustomException {
    public DietNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
