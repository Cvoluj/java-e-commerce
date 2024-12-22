package com.example.demo.dto.validation.annotation;

import com.example.demo.dto.validation.validators.SpacecraftTypeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = SpacecraftTypeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSpacecraftType {

    String message() default "Invalid spacecraft type. Allowed types: SPACESHIP, SHUTTLE, ROCKET, SATELLITE.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
