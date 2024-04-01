package com.ssafy.ibalance.child.dto.annotation;

import com.ssafy.ibalance.child.dto.validator.DateFormatValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateFormatValidator.class)
public @interface DateFormat {

    String message() default "입력 날짜는 현재 날짜보다 이전 날짜여야 하며 yyyy-MM-dd 형식이어야 합니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
