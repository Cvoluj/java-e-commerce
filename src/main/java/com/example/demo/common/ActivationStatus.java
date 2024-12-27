package com.example.demo.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivationStatus {

    SUCCESS("Activation Successful"),
    EXPIRED("Key Expired"),
    NOT_EXISTENT("Key Does Not Exist");

    private final String description;

    public static ActivationStatus fromName(String name) {
        for (ActivationStatus status : values()) {
            if (status.name().equalsIgnoreCase(name)) {
                return status;
            }
        }
        throw new IllegalArgumentException(String.format("Key activation status '%s' not found", name));
    }
}

