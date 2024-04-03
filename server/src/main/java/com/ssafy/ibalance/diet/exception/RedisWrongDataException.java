package com.ssafy.ibalance.diet.exception;

import com.ssafy.ibalance.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class RedisWrongDataException extends CustomException {

    public RedisWrongDataException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
