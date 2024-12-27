package com.example.demo.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class OrderEntryDto {

    @NotNull(message = "Spacecraft type cannot be null")
    String spacecraftType;

    @NotNull(message = "Spacecraft model cannot be null")
    String spacecraftModel;

    @NotNull(message = "Quantity cannot be null")
    Integer quantity;

}
