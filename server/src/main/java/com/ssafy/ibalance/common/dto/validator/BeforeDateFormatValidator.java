package com.ssafy.ibalance.common.dto.validator;

import com.ssafy.ibalance.child.dto.annotation.BeforeDateFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class BeforeDateFormatValidator extends DateValidator implements ConstraintValidator<BeforeDateFormat, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return super.checkValid(value, date -> !date.isAfter(LocalDate.now()));
    }
}
