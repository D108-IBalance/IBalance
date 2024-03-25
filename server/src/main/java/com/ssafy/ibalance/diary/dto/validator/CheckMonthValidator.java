package com.ssafy.ibalance.diary.dto.validator;

import com.ssafy.ibalance.diary.dto.annotation.CheckMonth;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckMonthValidator implements ConstraintValidator<CheckMonth, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && value >= 1 && value <= 12;
    }
}
