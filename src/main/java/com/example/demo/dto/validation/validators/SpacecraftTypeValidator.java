package com.example.demo.dto.validation.validators;

import com.example.demo.common.SpacecraftType;
import com.example.demo.dto.validation.annotation.ValidSpacecraftType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SpacecraftTypeValidator implements ConstraintValidator<ValidSpacecraftType, String> {

    @Override
    public void initialize(ValidSpacecraftType constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            SpacecraftType.fromName(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
