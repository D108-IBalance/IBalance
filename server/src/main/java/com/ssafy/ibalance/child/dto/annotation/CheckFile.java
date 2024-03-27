package com.ssafy.ibalance.child.dto.annotation;

import com.ssafy.ibalance.child.dto.validator.CheckFileValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckFileValidator.class)
public @interface CheckFile {

    String message() default "파일은 비어있을 수 없습니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
