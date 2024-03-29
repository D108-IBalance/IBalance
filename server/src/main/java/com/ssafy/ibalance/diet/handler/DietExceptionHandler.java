package com.ssafy.ibalance.diet.handler;

import com.ssafy.ibalance.common.type.ErrorResponse;
import com.ssafy.ibalance.diet.exception.DietNotFoundException;
import com.ssafy.ibalance.diet.exception.MenuInfoNotMatchException;
import com.ssafy.ibalance.diet.exception.NutritionServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.ssafy.ibalance.common.hadler.ExceptionHandlerTool.makeErrorResponse;

@RestControllerAdvice
public class DietExceptionHandler {

    @ExceptionHandler(NutritionServiceException.class)
    public List<ErrorResponse> nutritionServiceExceptionHandler(NutritionServiceException e) {
        return makeErrorResponse(e, "age");
    }

    @ExceptionHandler(DietNotFoundException.class)
    public List<ErrorResponse> dietNotFoundExceptionHandler(DietNotFoundException e) {
        return makeErrorResponse(e, "dietId");
    }

    @ExceptionHandler(MenuInfoNotMatchException.class)
    public List<ErrorResponse> menuInfoNotMatchExceptionHandler(MenuInfoNotMatchException e) {
        return makeErrorResponse(e, "menuId");
    }
}
