package com.example.demo.service;

import com.example.demo.domain.ProductDetails;
import com.example.demo.dto.activation.ActivationRequestDto;

import java.util.List;

public interface ProductService {

    ProductDetails addProduct(ProductDetails product);

    ProductDetails updateProduct(ProductDetails product);

    ProductDetails deleteProduct(Long id);

    ProductDetails getProductById(Long id);

    List<ProductDetails> getProducts();

    boolean activateProduct(ActivationRequestDto keyActivationRequestDto);
}
