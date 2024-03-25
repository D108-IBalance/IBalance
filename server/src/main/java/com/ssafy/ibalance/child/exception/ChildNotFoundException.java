package com.ssafy.ibalance.child.exception;

import com.ssafy.ibalance.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ChildNotFoundException extends CustomException {

    public ChildNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
