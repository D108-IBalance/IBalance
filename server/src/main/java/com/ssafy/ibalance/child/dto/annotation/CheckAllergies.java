package com.ssafy.ibalance.child.dto.annotation;

import com.ssafy.ibalance.child.dto.validator.AllergyNumberValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllergyNumberValidator.class)
public @interface CheckAllergies {

    String message() default "알러지 정보는 정수 배열로 입력해야 하며, 1~18 사이의 숫자를 입력해 주셔야 합니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
