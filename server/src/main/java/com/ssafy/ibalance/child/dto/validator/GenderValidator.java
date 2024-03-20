package com.ssafy.ibalance.child.dto.validator;

import com.ssafy.ibalance.child.dto.annotation.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<Gender, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            com.ssafy.ibalance.child.type.Gender.valueOf(value);
        } catch (IllegalArgumentException ex) {
            return false;
        }

        return true;
    }
}