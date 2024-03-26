package com.ssafy.ibalance.common.dto.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Function;

public class DateValidator {

    protected boolean checkValid(String value, Function<LocalDate, Boolean> checkFunction) {
        try {
            LocalDate inputPast = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            return checkFunction.apply(inputPast);
        } catch (DateTimeParseException | NullPointerException ignored) {}

        return false;
    }
}
