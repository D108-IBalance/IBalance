package com.ssafy.ibalance.common.controller;

import com.ssafy.ibalance.common.dto.response.CommonWrapperResponse;
import com.ssafy.ibalance.common.type.ErrorResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@RestControllerAdvice
public class CommonControllerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !Void.TYPE.equals(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(isErrorResponse(body)){
            List<ErrorResponse> errorResponses = (List<ErrorResponse>)body;
            return wrapResponse(errorResponses, errorResponses.getFirst().status());
        }
        return wrapResponse(body, HttpStatus.OK);
    }

    private boolean isErrorResponse(Object body){
        return body instanceof List<?> list && list.getFirst() instanceof ErrorResponse;
    }

    private CommonWrapperResponse wrapResponse(Object response, HttpStatus status){
        return CommonWrapperResponse.builder()
                .status(status.value())
                .data(response)
                .build();
    }
}
