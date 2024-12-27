package com.example.demo.util;

import com.example.demo.web.exception.ParamsViolationDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationDetailsUtils {

    private static final String VALIDATION_PROBLEM_TYPE = "https://example.com/validation-error";
    private static final String VALIDATION_PROBLEM_TITLE = "Field Validation Exception";

    private ValidationDetailsUtils() {
    }

    public static ProblemDetail getValidationErrorsProblemDetail(List<ParamsViolationDetails> violations) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Request validation failed");

        problemDetail.setType(URI.create(VALIDATION_PROBLEM_TYPE));
        problemDetail.setTitle(VALIDATION_PROBLEM_TITLE);

        List<Object> invalidParams = violations.stream()
                .map(violation -> new InvalidParam(violation.getFieldName(), violation.getReason()))
                .collect(Collectors.toList());

        problemDetail.setProperty("invalidParams", invalidParams);

        return problemDetail;
    }

    private record InvalidParam(String fieldName, String reason) {
    }
}
