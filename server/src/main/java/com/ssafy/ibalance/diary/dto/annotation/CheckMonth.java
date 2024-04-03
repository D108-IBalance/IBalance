package com.ssafy.ibalance.diary.dto.annotation;

import com.ssafy.ibalance.diary.dto.validator.CheckMonthValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckMonthValidator.class)
public @interface CheckMonth {

    String message() default "월은 1 이상 12 이하여야 합니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
