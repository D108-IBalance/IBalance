package com.ssafy.ibalance.member.exception;

import com.ssafy.ibalance.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ProviderNotSupportedException extends CustomException {
    public ProviderNotSupportedException(String message) {
        super(message, HttpStatus.NOT_ACCEPTABLE);
    }
}
