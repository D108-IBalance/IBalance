package com.ssafy.ibalance.diary.dto.annotation;

import com.ssafy.ibalance.diary.dto.validator.MealTimeValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MealTimeValidator.class)
public @interface CheckMealTime {
    String message() default "식사 시간은, BREAKFAST/LUNCH/DINNER/NONE 중 하나로 입력해 주세요.";

    Class[] groups() default {};

    Class[] payload() default {};
}
