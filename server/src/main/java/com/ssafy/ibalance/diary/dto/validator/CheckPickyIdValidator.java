package com.ssafy.ibalance.diary.dto.validator;

import com.ssafy.ibalance.diary.dto.annotation.CheckPickyId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class CheckPickyIdValidator implements ConstraintValidator<CheckPickyId, List<Long>> {

    @Override
    public boolean isValid(List<Long> longList, ConstraintValidatorContext constraintValidatorContext) {
        return longList.isEmpty() || longList.stream().allMatch(value -> value >= 1);
    }
}
