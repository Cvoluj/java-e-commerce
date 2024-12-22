package com.example.demo.service.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long productId) {
        super(String.format("Product with ID [%d] not found.", productId));
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
