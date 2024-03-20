package com.ssafy.ibalance.child.handler;

import com.ssafy.ibalance.child.exception.ChildAccessDeniedException;
import com.ssafy.ibalance.child.exception.ChildNotFoundException;
import com.ssafy.ibalance.common.type.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.ssafy.ibalance.common.hadler.ExceptionHandlerTool.makeErrorResponse;

@RestControllerAdvice
public class ChildExceptionHandler {

    @ExceptionHandler(ChildNotFoundException.class)
    public List<ErrorResponse> childNotFoundExceptionHandler(ChildNotFoundException e) {
        return makeErrorResponse(e, "childId");
    }

    @ExceptionHandler(ChildAccessDeniedException.class)
    public List<ErrorResponse> childAccessDeniedExceptionHandler(ChildAccessDeniedException e) {
        return makeErrorResponse(e, "childId");
    }
}
