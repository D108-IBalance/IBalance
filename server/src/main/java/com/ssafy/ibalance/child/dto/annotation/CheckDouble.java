package com.ssafy.ibalance.child.dto.annotation;

import com.ssafy.ibalance.child.dto.validator.DoubleValueValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoubleValueValidator.class)
public @interface CheckDouble {

    String message() default "키나 몸무게 데이터는 소수점 1자리까지만 입력해 주세요!";
    Class[] groups() default {};

    Class[] payload() default {};
}
