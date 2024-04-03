package com.ssafy.ibalance.common.exception;

import org.springframework.http.HttpStatus;

public class NotValidInfoOnFastAPIException extends CustomException {
    public NotValidInfoOnFastAPIException(String message) {
        super(message, HttpStatus.NOT_ACCEPTABLE);
    }
}
