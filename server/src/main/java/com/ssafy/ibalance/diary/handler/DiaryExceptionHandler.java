package com.ssafy.ibalance.diary.handler;

import com.ssafy.ibalance.common.type.ErrorResponse;
import com.ssafy.ibalance.diary.exception.DiaryNotWrittenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.ssafy.ibalance.common.hadler.ExceptionHandlerTool.makeErrorResponse;

@RestControllerAdvice
public class DiaryExceptionHandler {

    @ExceptionHandler(DiaryNotWrittenException.class)
    public List<ErrorResponse> diaryNotWrittenExceptionHandler(DiaryNotWrittenException e) {
        return makeErrorResponse(e, "dietId");
    }
}
