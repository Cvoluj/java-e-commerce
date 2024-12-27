package com.example.demo.service.exception;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProblemDetails {
    private String type;
    private String title;
    private int status;
    private String detail;
    private String instance;
}

