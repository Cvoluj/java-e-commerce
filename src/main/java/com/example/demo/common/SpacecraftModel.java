package com.example.demo.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpacecraftModel {

    FALCON_9("Falcon 9"),
    DRAGON("Dragon"),
    STARSHIP("Starship"),
    SATURN_V("Saturn V");

    private final String name;

    public static SpacecraftModel fromName(String name) {
        for (SpacecraftModel model : values()) {
            if (model.name.equalsIgnoreCase(name)) {
                return model;
            }
        }
        throw new IllegalArgumentException(String.format("Spacecraft model '%s' not found", name));
    }
}
