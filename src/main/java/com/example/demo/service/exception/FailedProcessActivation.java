package com.example.demo.service.exception;

public class FailedProcessActivation extends RuntimeException {

    public FailedProcessActivation(String keyId) {
        super(String.format("Activation process failed for key ID: %s", keyId));
    }

    public FailedProcessActivation(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedProcessActivation(String keyId, String additionalMessage) {
        super(String.format("Activation process failed for key ID: %s. %s", keyId, additionalMessage));
    }
}
