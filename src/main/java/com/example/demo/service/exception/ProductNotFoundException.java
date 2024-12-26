package com.example.demo.service.exception;

public class ProductNotFoundException extends RuntimeException {
    private static final String PRODUCT_NOT_FOUND_MESSAGE_TEMPLATE = "Product with ID [%d] not found.";

    public ProductNotFoundException(Long productId) {
        super(String.format(PRODUCT_NOT_FOUND_MESSAGE_TEMPLATE, productId));
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
