package com.ssafy.ibalance.child.handler;

import com.ssafy.ibalance.common.type.ErrorResponse;
import com.ssafy.ibalance.child.exception.NotImageException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.ssafy.ibalance.common.hadler.ExceptionHandlerTool.makeErrorResponse;

@RestControllerAdvice
public class FileExceptionHandler {

    @ExceptionHandler(NotImageException.class)
    public List<ErrorResponse> notImageExceptionHandler(NotImageException e) {
        return makeErrorResponse(e, "file");
    }
}
