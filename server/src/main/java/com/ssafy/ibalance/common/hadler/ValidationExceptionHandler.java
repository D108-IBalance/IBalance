package com.ssafy.ibalance.common.hadler;

import com.ssafy.ibalance.common.type.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.OK)
    public List<ErrorResponse> processValidationExceptionHandler(BindException e){
        return e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> ErrorResponse.builder()
                        .message(fieldError.getDefaultMessage())
                        .errorType(fieldError.getCode())
                        .fieldName(fieldError.getField())
                        .status(HttpStatus.BAD_REQUEST)
                        .build())
                .toList();
    }
}
