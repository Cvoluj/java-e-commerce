package com.example.demo.dto.customer;

import com.example.demo.common.MissionType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Value
@Builder
@Jacksonized
@Validated
public class CustomerDetailsDto {

    @NotBlank(message = "The client name cannot be empty.")
    @Size(max = 100, message = "The customer name must not exceed 100 characters.")
    String name;

    @NotBlank(message = "The phone number cannot be empty.")
    @Size(max = 15, message = "The phone number must not exceed 15 characters.")
    String phoneNumber;

    @Email(message = "Email format is incorrect.")
    @NotBlank(message = "Email cannot be empty.")
    String email;

    @NotEmpty(message = "The list of mission types cannot be empty.")
    List<MissionType> missionTypes;
}
