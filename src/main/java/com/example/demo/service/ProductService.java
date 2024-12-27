package com.example.demo.service;

import com.example.demo.domain.ProductDetails;
import com.example.demo.dto.activation.ActivationRequestDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDetails addProduct(ProductDetails product);

    ProductDetails updateProduct(ProductDetails product);

    ProductDetails deleteProduct(UUID id);

    ProductDetails getProductById(UUID id);

    List<ProductDetails> getProducts();

    boolean activateProduct(ActivationRequestDto keyActivationRequestDto);
}
