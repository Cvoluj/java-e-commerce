package com.example.demo.service.impl;

import com.example.demo.common.ActivationStatus;
import com.example.demo.common.MissionType;
import com.example.demo.common.SpacecraftType;
import com.example.demo.domain.ProductDetails;
import com.example.demo.dto.activation.ActivationRequestDto;
import com.example.demo.dto.activation.ActivationResponseDto;
import com.example.demo.service.exception.FailedProcessActivation;
import com.example.demo.service.exception.ProductNotFoundException;
import com.example.demo.service.ActivationService;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ActivationService keyActivationService;

    private List<ProductDetails> products = buildProductDetailsMock();

    @Override
    public ProductDetails addProduct(ProductDetails product) {
        if (products.stream().anyMatch(p -> p.getId().equals(product.getId()))) {
            throw new IllegalArgumentException(String.format("Product with ID %d already exists.", product.getId()));
        }

        products.add(product);

        log.info("Added product: {}", product);
        return product;
    }

    @Override
    public ProductDetails updateProduct(ProductDetails product) {
        ProductDetails existingProduct = getProductById(product.getId());
        ProductDetails updatedProduct = existingProduct.toBuilder()
                .id(product.getId())
                .title(product.getTitle())
                .shortDescription(product.getShortDescription())
                .price(product.getPrice())
                .type(product.getType())
                .tags(product.getTags())
                .mission(product.getMission())
                .build();

        products = products.stream()
                .map(p -> p.getId().equals(product.getId()) ? updatedProduct : p)
                .collect(Collectors.toList());

        log.info("Updated product: {}", updatedProduct);
        return updatedProduct;
    }

    @Override
    public ProductDetails deleteProduct(Long id) {
        ProductDetails productToRemove = getProductById(id);

        products = products.stream()
                .filter(product -> !product.getId().equals(id))
                .collect(Collectors.toList());

        log.info("Deleted product with ID: {}", id);
        return productToRemove;
    }

    @Override
    public ProductDetails getProductById(Long id) {
        return products.stream()
                .filter(product -> product.getId().toString().equals(id.toString()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));
    }

    @Override
    public List<ProductDetails> getProducts() {
        return products;
    }

    @Override
    public boolean activateProduct(ActivationRequestDto keyActivationRequestDto) {
        log.info("Activating product with key: {}", keyActivationRequestDto.getKey());
        ActivationResponseDto activationResponseDto = keyActivationService.processKeyActivation(keyActivationRequestDto);
        if (ActivationStatus.EXPIRED.equals(activationResponseDto.getStatus())
                || ActivationStatus.NOT_EXISTENT.equals(activationResponseDto.getStatus())) {
            throw new FailedProcessActivation(keyActivationRequestDto.getKey());
        }

        return true;
    }

    private List<ProductDetails> buildProductDetailsMock() {
        return new ArrayList<>(List.of(
                ProductDetails.builder()
                        .id(1L)
                        .title("Falcon 9")
                        .shortDescription("A reusable rocket designed for commercial missions")
                        .price(62000000.0)
                        .type(SpacecraftType.ROCKET)
                        .mission(MissionType.COMMERCIAL)
                        .tags(List.of("space", "rocket", "commercial"))
                        .build(),
                ProductDetails.builder()
                        .id(2L)
                        .title("Dragon")
                        .shortDescription("A spacecraft designed for cargo and crew missions to the ISS")
                        .price(200000000.0)
                        .type(SpacecraftType.SHUTTLE)
                        .mission(MissionType.SCIENTIFIC)
                        .tags(List.of("space", "shuttle", "scientific"))
                        .build(),
                ProductDetails.builder()
                        .id(3L)
                        .title("Starship")
                        .shortDescription("A fully reusable super heavy-lift launch vehicle")
                        .price(1000000000.0)
                        .type(SpacecraftType.ROCKET)
                        .mission(MissionType.EXPLORATION)
                        .tags(List.of("space", "rocket", "exploration"))
                        .build(),
                ProductDetails.builder()
                        .id(4L)
                        .title("Soyuz")
                        .shortDescription("A series of spacecraft designed for manned missions to the ISS")
                        .price(80000000.0)
                        .type(SpacecraftType.SHUTTLE)
                        .mission(MissionType.COMMERCIAL)
                        .tags(List.of("space", "shuttle", "commercial"))
                        .build(),
                ProductDetails.builder()
                        .id(5L)
                        .title("Voyager")
                        .shortDescription("A spacecraft designed for deep space exploration")
                        .price(865000000.0)
                        .type(SpacecraftType.SHUTTLE)
                        .mission(MissionType.SCIENTIFIC)
                        .tags(List.of("space", "probe", "scientific"))
                        .build()
        ));
    }
}
