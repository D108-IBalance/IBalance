package com.ssafy.ibalance.diary.dto.annotation;

import com.ssafy.ibalance.diary.dto.validator.CheckMenuRateValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckMenuRateValidator.class)
public @interface CheckMenuRate {

    String message() default "메뉴 별점은 1점부터 5점까지 입력할 수 있습니다.";

    Class[] groups() default {};

    Class[] payload() default {};
}
