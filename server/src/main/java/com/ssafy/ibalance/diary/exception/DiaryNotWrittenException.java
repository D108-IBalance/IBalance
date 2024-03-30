package com.ssafy.ibalance.diary.exception;

import com.ssafy.ibalance.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class DiaryNotWrittenException extends CustomException {
    public DiaryNotWrittenException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
