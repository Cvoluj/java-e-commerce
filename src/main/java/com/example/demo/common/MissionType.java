package com.example.demo.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum MissionType {
    EXPLORATION("Exploration"),
    SCIENTIFIC("Scientific"),
    COMMERCIAL("Commercial"),
    MILITARY("Military"),
    TOURISM("Tourism");

    private final String name;

    public static MissionType fromName(String name) {
        for (MissionType mission : values()) {
            if (mission.name.equalsIgnoreCase(name)) {
                return mission;
            }
        }
        throw new IllegalArgumentException(String.format("Mission type '%s' not found", name));
    }
}
