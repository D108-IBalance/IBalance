package com.ssafy.ibalance.diet.dto.validator;

import com.ssafy.ibalance.diet.dto.annotation.CheckPeriodUnit;
import com.ssafy.ibalance.diet.type.PeriodUnit;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PeriodUnitValidator implements ConstraintValidator<CheckPeriodUnit, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            PeriodUnit.valueOf(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
