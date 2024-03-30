package com.ssafy.ibalance.diary.dto.validator;

import com.ssafy.ibalance.diary.dto.annotation.CheckMealTime;
import com.ssafy.ibalance.diet.type.MealTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MealTimeValidator implements ConstraintValidator<CheckMealTime, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            MealTime.valueOf(value);
            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            return value == null || value.isEmpty();
        }
    }
}
