package com.example.demo.domain.order;

import com.example.demo.common.SpacecraftType;
import com.example.demo.common.SpacecraftModel;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class SpacecraftOrderEntry {
    SpacecraftType spacecraftType;
    SpacecraftModel spacecraftModel;
    int quantity;
}
