package com.ssafy.ibalance.diet.exception;

import com.ssafy.ibalance.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NutritionServiceException extends CustomException {
    public NutritionServiceException(String message) {
        super(message, HttpStatus.NOT_ACCEPTABLE);
    }
}
