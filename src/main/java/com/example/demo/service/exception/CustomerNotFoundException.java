package com.example.demo.service.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long customerId) {
        super(String.format("Customer with ID %d not found", customerId));
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(Long customerId, String additionalMessage) {
        super(String.format("Customer with ID %d not found. %s", customerId, additionalMessage));
    }
}
