package com.example.demo.domain.order;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class SpacecraftOrder {
    String orderId;
    UUID transactionId;
    List<SpacecraftOrderEntry> entries;
    String cartId;
    String customerId;
    Double total;
}
