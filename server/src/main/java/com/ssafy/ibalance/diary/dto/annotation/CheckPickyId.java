package com.ssafy.ibalance.diary.dto.annotation;

import com.ssafy.ibalance.diary.dto.validator.CheckPickyIdValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckPickyIdValidator.class)
public @interface CheckPickyId {

    String message() default "편식 식재료 아이디는 1 이상의 정수여야 합니다.";

    Class[] groups() default {};

    Class[] payload() default {};
}
