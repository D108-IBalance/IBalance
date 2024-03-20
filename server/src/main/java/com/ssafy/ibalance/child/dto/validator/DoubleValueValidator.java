package com.ssafy.ibalance.child.dto.validator;

import com.ssafy.ibalance.child.dto.annotation.CheckDouble;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DoubleValueValidator implements ConstraintValidator<CheckDouble, Double> {
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext constraintValidatorContext) {
        return value * 10 == Math.floor(value * 10);
    }
}
