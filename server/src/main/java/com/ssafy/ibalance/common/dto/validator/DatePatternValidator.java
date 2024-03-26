package com.ssafy.ibalance.common.dto.validator;

import com.ssafy.ibalance.child.dto.annotation.BeforeDateFormat;
import com.ssafy.ibalance.common.dto.annotation.DatePattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DatePatternValidator extends DateValidator implements ConstraintValidator<DatePattern, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return super.checkValid(value, date -> true);
    }
}
