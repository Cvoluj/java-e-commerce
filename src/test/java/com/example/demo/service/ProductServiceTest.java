package com.example.demo.service;

import com.example.demo.common.ActivationStatus;
import com.example.demo.common.MissionType;
import com.example.demo.common.SpacecraftType;
import com.example.demo.domain.ProductDetails;
import com.example.demo.dto.activation.ActivationRequestDto;
import com.example.demo.dto.activation.ActivationResponseDto;
import com.example.demo.service.exception.FailedProcessActivation;
import com.example.demo.service.exception.ProductNotFoundException;
import com.example.demo.service.impl.ProductServiceImpl;
import com.example.demo.service.impl.ActivationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private final UUID wrongUUID = UUID.fromString("1a740b1a-1e33-4479-bb41-3d9e99f31a2b");

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ActivationServiceImpl activationService;

    private final List<ProductDetails> products = buildProductDetailsMock();


    @Test
    @DisplayName("Should add a new product")
    void shouldAddProduct() {
        UUID newUUID = UUID.randomUUID();
        ProductDetails newProduct = ProductDetails.builder()
                .id(newUUID)
                .title("Apollo")
                .shortDescription("Spacecraft for moon missions")
                .price(50000000.0)
                .type(SpacecraftType.SHUTTLE)
                .mission(MissionType.EXPLORATION)
                .tags(List.of("space", "shuttle", "exploration"))
                .build();

        ProductDetails addedProduct = productService.addProduct(newProduct);


        assertNotNull(addedProduct);
        assertEquals(newUUID, addedProduct.getId());
    }

    @Test
    @DisplayName("Should find product by ID")
    void shouldFindProductById() {
        UUID productId = UUID.fromString("3c4632d1-da13-44f5-b3b5-68fe49d179ae");
        ProductDetails expectedProduct = products.get(0);

        ProductDetails foundProduct = productService.getProductById(productId);

        assertNotNull(foundProduct);
        assertEquals(expectedProduct.getId(), foundProduct.getId());
        assertEquals(expectedProduct.getTitle(), foundProduct.getTitle());
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException when product not found")
    void shouldThrowProductNotFoundException() {
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(wrongUUID));
    }

    @Test
    @DisplayName("Should update existing product")
    void shouldUpdateProduct() {
        ProductDetails existingProduct = products.get(0);
        ProductDetails updatedProduct = existingProduct.toBuilder()
                .title("Updated Falcon 9")
                .price(63000000.0)
                .build();

        ProductDetails result = productService.updateProduct(updatedProduct);

        assertEquals("Updated Falcon 9", result.getTitle());
        assertEquals(63000000.0, result.getPrice());
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException when updating non-existing product")
    void shouldThrowProductNotFoundExceptionWhenUpdatingProduct() {
        ProductDetails nonExistingProduct = ProductDetails.builder()
                .id(wrongUUID)
                .title("Non-Existing Product")
                .price(1000000.0)
                .build();

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(nonExistingProduct.getId()));
    }

    @Test
    @DisplayName("Should activate product with valid key")
    void shouldActivateProduct() {
        ActivationRequestDto activationRequest = ActivationRequestDto.builder()
                .key("valid-key")
                .customerId("12345")
                .status(ActivationStatus.SUCCESS)
                .build();

        ActivationResponseDto activationResponse = ActivationResponseDto.builder()
                .key("valid-key")
                .status(ActivationStatus.SUCCESS)
                .productId("1")
                .build();

        when(activationService.processKeyActivation(activationRequest)).thenReturn(activationResponse);

        boolean isActivated = productService.activateProduct(activationRequest);

        assertTrue(isActivated);
    }

    @Test
    @DisplayName("Should throw FailedProcessActivation when key activation fails")
    void shouldThrowFailedProcessActivation() {
        ActivationRequestDto activationRequest = ActivationRequestDto.builder()
                .key("invalid-key")
                .customerId("12345")
                .status(ActivationStatus.NOT_EXISTENT)
                .build();

        ActivationResponseDto activationResponse = ActivationResponseDto.builder()
                .key("invalid-key")
                .status(ActivationStatus.NOT_EXISTENT)
                .productId("1")
                .build();

        when(activationService.processKeyActivation(activationRequest)).thenReturn(activationResponse);

        assertThrows(FailedProcessActivation.class, () -> productService.activateProduct(activationRequest));
    }

    private List<ProductDetails> buildProductDetailsMock() {
        return List.of(
                ProductDetails.builder()
                        .id(UUID.fromString("3c4632d1-da13-44f5-b3b5-68fe49d179ae"))
                        .title("Falcon 9")
                        .shortDescription("A reusable rocket designed for commercial missions")
                        .price(62000000.0)
                        .type(SpacecraftType.ROCKET)
                        .mission(MissionType.COMMERCIAL)
                        .tags(List.of("space", "rocket", "commercial"))
                        .build(),
                ProductDetails.builder()
                        .id(UUID.fromString("db622b70-9d51-48f6-a4be-bff435d9a099"))
                        .title("Dragon")
                        .shortDescription("A spacecraft designed for cargo and crew missions to the ISS")
                        .price(200000000.0)
                        .type(SpacecraftType.SHUTTLE)
                        .mission(MissionType.SCIENTIFIC)
                        .tags(List.of("space", "shuttle", "scientific"))
                        .build()
        );
    }
}
