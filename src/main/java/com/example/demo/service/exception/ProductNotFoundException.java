package com.example.demo.service.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    private static final String PRODUCT_NOT_FOUND_MESSAGE_TEMPLATE = "Product with ID [%s] not found.";

    public ProductNotFoundException(UUID productId) {
        super(String.format(PRODUCT_NOT_FOUND_MESSAGE_TEMPLATE, productId.toString()));
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
