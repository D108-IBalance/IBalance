package com.ssafy.ibalance.diet.dto.annotation;

import com.ssafy.ibalance.diet.dto.validator.PeriodUnitValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PeriodUnitValidator.class)
public @interface CheckPeriodUnit {

    String message() default "limit 값은 WEEKLY / MONTHLY (대문자) 로만 입력해 주셔야 합니다.";

    Class[] groups() default {};

    Class[] payload() default {};
}
