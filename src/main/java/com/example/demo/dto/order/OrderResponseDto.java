package com.example.demo.dto.order;

import lombok.Builder;
import lombok.Value;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class OrderResponseDto {

    String orderId;
    UUID transactionId;
}
