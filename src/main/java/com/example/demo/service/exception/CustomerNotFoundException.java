package com.example.demo.service.exception;

public class CustomerNotFoundException extends RuntimeException {
    private static final String CUSTOMER_NOT_FOUND_MESSAGE_TEMPLATE = "Customer with ID %d not found";

    public CustomerNotFoundException(Long customerId) {
        super(String.format(CUSTOMER_NOT_FOUND_MESSAGE_TEMPLATE, customerId));
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(Long customerId, String additionalMessage) {
        super(String.format("Customer with ID %d not found. %s", customerId, additionalMessage));
    }
}
