package com.ssafy.ibalance.common.dto.annotation;

import com.ssafy.ibalance.common.dto.validator.DatePatternValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DatePatternValidator.class)
public @interface DatePattern {

    String message() default "입력 날짜는 yyyy-MM-dd 형식이어야 합니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
