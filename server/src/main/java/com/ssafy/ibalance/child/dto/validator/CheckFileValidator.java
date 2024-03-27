package com.ssafy.ibalance.child.dto.validator;

import com.ssafy.ibalance.child.dto.annotation.CheckFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class CheckFileValidator implements ConstraintValidator<CheckFile, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile image, ConstraintValidatorContext constraintValidatorContext) {
        return !(image.isEmpty() || Objects.isNull(image.getOriginalFilename()));
    }
}
