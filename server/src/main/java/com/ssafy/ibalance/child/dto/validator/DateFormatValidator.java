package com.ssafy.ibalance.child.dto.validator;

import com.ssafy.ibalance.child.dto.annotation.DateFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDate inputPast = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate now = LocalDate.now();

            if (inputPast.isAfter(now)) {
                return false;
            }
        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }

        return true;
    }
}