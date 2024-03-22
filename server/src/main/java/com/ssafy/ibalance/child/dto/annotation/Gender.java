package com.ssafy.ibalance.child.dto.annotation;

import com.ssafy.ibalance.child.dto.validator.GenderValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenderValidator.class)
public @interface Gender {

    String message() default "성별은 MALE 또는 FEMALE 만 입력 가능합니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
