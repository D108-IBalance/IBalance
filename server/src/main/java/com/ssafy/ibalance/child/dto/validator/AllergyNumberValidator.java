package com.ssafy.ibalance.child.dto.validator;

import com.ssafy.ibalance.child.dto.annotation.CheckAllergies;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class AllergyNumberValidator implements ConstraintValidator<CheckAllergies, List<Integer>> {

    private static final Integer min = 1;
    private static final Integer max = 18;

    @Override
    public boolean isValid(List<Integer> list, ConstraintValidatorContext constraintValidatorContext) {
        return list.isEmpty() || list.stream().allMatch(n -> min <= n && n <= max);
    }
}
