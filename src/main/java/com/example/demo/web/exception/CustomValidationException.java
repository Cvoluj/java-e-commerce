package com.example.demo.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomValidationException extends RuntimeException {
    private final List<ValidationError> errors;

    public CustomValidationException(String message, List<ValidationError> errors) {
        super(message);
        this.errors = errors;
    }

    @Getter
    @Setter
    public static class ValidationError {
        @JsonProperty("fieldName")
        private String fieldName;

        @JsonProperty("reason")
        private String reason;

        public ValidationError(String fieldName, String reason) {
            this.fieldName = fieldName;
            this.reason = reason;
        }
    }
}
