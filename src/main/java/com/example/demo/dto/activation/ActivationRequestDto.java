package com.example.demo.dto.activation;

import com.example.demo.common.ActivationStatus;
import lombok.extern.jackson.Jacksonized;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class ActivationRequestDto {

    String key;
    String customerId;
    ActivationStatus status;

}
