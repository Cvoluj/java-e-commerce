package com.example.demo.domain;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class SpacecraftDetails {
    UUID id;
    String name;
    String SpacecraftType;
    String SpacecraftModel;
    String Mission;
    String description;
}
