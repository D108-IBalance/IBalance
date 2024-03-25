package com.ssafy.ibalance.child.exception;

import com.ssafy.ibalance.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ChildAccessDeniedException extends CustomException {

    public ChildAccessDeniedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
