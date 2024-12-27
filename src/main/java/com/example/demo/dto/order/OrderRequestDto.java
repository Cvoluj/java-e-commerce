package com.example.demo.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Value;
import java.util.List;


@Value
@Builder(toBuilder = true)
public class OrderRequestDto {

    @NotNull(message = "Order context cannot be null")
    List <OrderEntryDto> orderContext;

    @NotNull(message = "Total price cannot be null")
    @Min(value = 0, message = "Total price cannot be less than 0")
    Double totalPrice;

}
