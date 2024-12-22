package com.example.demo.dto.validation.validators;

import com.example.demo.common.MissionType;
import com.example.demo.dto.validation.annotation.ValidMissionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class MissionTypeValidator implements ConstraintValidator<ValidMissionType, String> {

    @Override
    public void initialize(ValidMissionType constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        try {
            MissionType.fromName(value);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

}
