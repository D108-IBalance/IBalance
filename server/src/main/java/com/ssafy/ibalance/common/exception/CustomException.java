package com.ssafy.ibalance.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException{

    protected HttpStatus status;

    public CustomException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}
