package com.ssafy.ibalance.diet.type;

import java.time.LocalDate;
import java.util.function.Function;

public enum PeriodUnit {

    WEEKLY(date -> date.minusWeeks(1)),
    MONTHLY(date -> date.minusMonths(1));

    private final Function<LocalDate, LocalDate> dateFunction;

    PeriodUnit(Function<LocalDate, LocalDate> dateFunction) {
        this.dateFunction = dateFunction;
    }

    public static LocalDate getStartDate(PeriodUnit periodUnit) {
        return periodUnit.dateFunction.apply(LocalDate.now());
    }
}
