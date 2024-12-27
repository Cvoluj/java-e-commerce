package com.example.demo.web;

import com.example.demo.BaseIntegration;
import com.example.demo.service.impl.ProductServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.domain.ProductDetails;
import com.example.demo.dto.product.ProductDetailsDto;
import com.example.demo.objects.BuildProducts;
import com.example.demo.service.ProductService;
import com.example.demo.service.mapper.ProductMapper;
import com.example.demo.service.exception.ProductNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Product Controller IT")
@Tag("product-service")
public class ProductControllerIT extends BaseIntegration {
    public static final UUID uuid1 = UUID.fromString("3c4632d1-da13-44f5-b3b5-68fe49d179ae");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductController productController;

    @Autowired
    private ProductMapper productMapper;



    @Test
    @SneakyThrows
    void shouldCreateProduct() throws JsonProcessingException, Exception {
        ProductDetailsDto productDetailsDto = BuildProducts.buildProductDetailsDto();

        when(productService.addProduct(any())).thenReturn(productMapper.toProductDetails(productDetailsDto));
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(productDetailsDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(productDetailsDto.getTitle()))
                .andExpect(jsonPath("$.price").value(productDetailsDto.getPrice()))
                .andExpect(jsonPath("$.type").value(Matchers.equalToIgnoringCase(productDetailsDto.getType())))
                .andExpect(jsonPath("$.mission").value(Matchers.equalToIgnoringCase("EXPLORATION")));

    }

    @Test
    @SneakyThrows
    void shouldThrowsValidationExceptionWithNoValidationProductFields() throws JsonProcessingException, Exception {
        ProductDetailsDto dto = productMapper.toProductDetailsDto(BuildProducts.buildInvalidProductDetails());

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("urn:problem-type:validation-error"))
                .andExpect(jsonPath("$.title").value("Field Validation Exception"))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.detail").value("Request validation failed"))
                .andExpect(jsonPath("$.invalidParams", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.invalidParams[*].fieldName",
                        containsInAnyOrder(
                                "addProduct.productDto.tags[0].<list element>",
                                "addProduct.productDto.title",
                                "addProduct.productDto.mission",
                                "addProduct.productDto.price",
                                "addProduct.productDto.type"
                        )));
    }

    @Test
    @SneakyThrows
    void shouldFindByIdProduct() throws JsonProcessingException, Exception {
        ProductDetails product = BuildProducts.buildProductDetails();

        when(productService.getProductById(uuid1)).thenReturn(product);

        mockMvc.perform(get("/api/v1/products/{id}", uuid1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(product.getTitle()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.type").value(Matchers.equalToIgnoringCase(String.valueOf(product.getType()))))
                .andExpect(jsonPath("$.mission").value(Matchers.equalToIgnoringCase(String.valueOf(product.getMission()))));

        verify(productService, times(1)).getProductById(uuid1);
    }

    @Test
    @SneakyThrows
    void shouldThrowsProductNotFoundException() throws JsonProcessingException, Exception {
        ProductNotFoundException exception = new ProductNotFoundException(uuid1);
        doThrow(exception).when(productService).getProductById(uuid1);

        mockMvc.perform(get("/api/v1/products/{id}", uuid1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("product-not-found"))
                .andExpect(jsonPath("$.title").value("Product Not Found"));

        assertThrows(ProductNotFoundException.class, () -> {
            productController.getProductById(uuid1);
        });

        verify(productService, times(2)).getProductById(uuid1);
    }


}
