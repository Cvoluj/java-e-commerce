package com.example.demo.dto.validation.annotation;

import com.example.demo.dto.validation.validators.MissionTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MissionTypeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMissionType {

    String message() default "Invalid mission type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
