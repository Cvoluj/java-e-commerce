package com.example.demo.web;

import com.example.demo.domain.ProductDetails;
import com.example.demo.dto.product.ProductDetailsDto;
import com.example.demo.dto.product.ProductDetailsListDto;
import com.example.demo.dto.activation.ActivationRequestDto;
import com.example.demo.service.ProductService;
import com.example.demo.service.mapper.ProductMapper;
import com.example.demo.web.exception.CustomValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<ProductDetailsListDto> getAllProducts() {
        log.info("Fetching all products...");
        List<ProductDetails> products = productService.getProducts();
        ProductDetailsListDto productListDto = productMapper.toProductDetailsListDto(products);
        return ResponseEntity.ok(productListDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsDto> getProductById(@PathVariable Long id) {
        log.info("Fetching product with ID: {}", id);
        ProductDetails product = productService.getProductById(id);
        ProductDetailsDto productDto = productMapper.toProductDetailsDto(product);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductDetailsDto productDto, BindingResult bindingResult) {
        log.info("Adding new product with ID: {}", productDto.getId());

        if (bindingResult.hasErrors()) {
            List<CustomValidationException.ValidationError> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> new CustomValidationException.ValidationError(
                            error.getField(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errors);
        }


        ProductDetailsDto addedProductDto = productMapper.toProductDetailsDto(
                productService.addProduct(productMapper.toProductDetails(productDto))
        );
        return ResponseEntity.status(201).body(addedProductDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDetailsDto> updateProductById(@PathVariable Long id, @RequestBody @Valid ProductDetailsDto product) {
        log.info("Updating product with ID: {}", id);
        ProductDetailsDto updatedProduct = productMapper.toProductDetailsDto(
            productService.updateProduct(productMapper.toProductDetails(id, product))
        );
        return ResponseEntity.ok(updatedProduct);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDetailsDto> deleteProductById(@PathVariable Long id) {
        log.info("Deleting product with ID: {}", id);
        ProductDetailsDto deletedProduct = productMapper.toProductDetailsDto(
            productService.deleteProduct(id)
        );
        return ResponseEntity.ok(deletedProduct);
    }

    @PostMapping("/activate")
    public ResponseEntity<Boolean> activateProduct(@RequestBody @Valid ActivationRequestDto activationRequest) {
        log.info("Activating product with key: {}", activationRequest.getKey());
        boolean isActivated = productService.activateProduct(activationRequest);
        return ResponseEntity.ok(isActivated);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, Object> response = new HashMap<>();

        response.put("type", "urn:problem-type:validation-error");
        response.put("title", "Field Validation Exception");
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("detail", "Request validation failed");

        List<Map<String, String>> invalidParams = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            Map<String, String> errorDetail = new HashMap<>();
            errorDetail.put("fieldName", violation.getPropertyPath().toString());
            errorDetail.put("message", violation.getMessage());
            invalidParams.add(errorDetail);
        }

        response.put("invalidParams", invalidParams);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
