package com.ssafy.ibalance.child.handler;

import com.ssafy.ibalance.child.exception.ChildNotFoundException;
import com.ssafy.ibalance.common.type.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static com.ssafy.ibalance.common.hadler.ExceptionHandlerTool.makeErrorResponse;

public class ChildExceptionHandler {

    @ExceptionHandler(ChildNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List<ErrorResponse> childNotFoundExceptionHandler(ChildNotFoundException e) {
        return makeErrorResponse(e, "childId");
    }
}
