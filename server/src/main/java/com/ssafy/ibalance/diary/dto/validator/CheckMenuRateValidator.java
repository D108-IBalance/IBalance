package com.ssafy.ibalance.diary.dto.validator;

import com.ssafy.ibalance.diary.dto.annotation.CheckMenuRate;
import com.ssafy.ibalance.diary.dto.request.MenuRateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class CheckMenuRateValidator implements ConstraintValidator<CheckMenuRate, List<MenuRateRequest>> {

    @Override
    public boolean isValid(List<MenuRateRequest> menuRateRequests, ConstraintValidatorContext constraintValidatorContext) {
        return menuRateRequests.size() == 4 && menuRateRequests.stream()
                .allMatch(req -> !req.getMenuId().isBlank() && !req.getMenuId().isEmpty()
                        && 1 <= req.getRate() && req.getRate() <= 5);
    }
}
