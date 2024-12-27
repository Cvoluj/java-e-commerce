package com.example.demo.domain.order;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class SpacecraftOrderContext {
    String cartId;
    String customerId;
    List<SpacecraftOrderEntry> orderEntries;
    Double totalPrice;
}
