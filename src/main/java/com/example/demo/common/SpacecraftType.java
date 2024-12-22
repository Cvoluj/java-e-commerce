package com.example.demo.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum SpacecraftType {

    SPACESHIP("Spaceship"),
    SHUTTLE("Shuttle"),
    ROCKET("Rocket"),
    SATELLITE("Satellite");

    private final String name;

    public static SpacecraftType fromName(String name) {
        for (SpacecraftType spacecraft : values()) {
            if (spacecraft.name.equalsIgnoreCase(name)) {
                return spacecraft;
            }
        }
        throw new IllegalArgumentException(String.format("Spacecraft type '%s' not found", name));
    }
}
