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
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final UUID uuid1 = UUID.fromString("3c4632d1-da13-44f5-b3b5-68fe49d179ae");
    private final UUID uuid2 = UUID.fromString("b73be0de-93cf-46fa-b686-de7f29b45990");
    private final UUID uuid3 = UUID.fromString("114337c5-9611-4781-bb2c-b47a0e1ac9fc");
    private final UUID uuid4 = UUID.fromString("bf769b32-4e43-4b63-a994-71458a80c363");
    private final UUID uuid5 = UUID.fromString("21105d6f-63af-44ea-a36e-8e8e11df10ed");
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
    public ProductDetails deleteProduct(UUID id) {
        ProductDetails productToRemove = getProductById(id);

        products = products.stream()
                .filter(product -> !product.getId().equals(id))
                .collect(Collectors.toList());

        log.info("Deleted product with ID: {}", id);
        return productToRemove;
    }

    @Override
    public ProductDetails getProductById(UUID id) {
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
                        .id(uuid1)
                        .title("Falcon 9")
                        .shortDescription("A reusable rocket designed for commercial missions")
                        .price(62000000.0)
                        .type(SpacecraftType.ROCKET)
                        .mission(MissionType.COMMERCIAL)
                        .tags(List.of("space", "rocket", "commercial"))
                        .build(),
                ProductDetails.builder()
                        .id(uuid2)
                        .title("Dragon")
                        .shortDescription("A spacecraft designed for cargo and crew missions to the ISS")
                        .price(200000000.0)
                        .type(SpacecraftType.SHUTTLE)
                        .mission(MissionType.SCIENTIFIC)
                        .tags(List.of("space", "shuttle", "scientific"))
                        .build(),
                ProductDetails.builder()
                        .id(uuid3)
                        .title("Starship")
                        .shortDescription("A fully reusable super heavy-lift launch vehicle")
                        .price(1000000000.0)
                        .type(SpacecraftType.ROCKET)
                        .mission(MissionType.EXPLORATION)
                        .tags(List.of("space", "rocket", "exploration"))
                        .build(),
                ProductDetails.builder()
                        .id(uuid4)
                        .title("Soyuz")
                        .shortDescription("A series of spacecraft designed for manned missions to the ISS")
                        .price(80000000.0)
                        .type(SpacecraftType.SHUTTLE)
                        .mission(MissionType.COMMERCIAL)
                        .tags(List.of("space", "shuttle", "commercial"))
                        .build(),
                ProductDetails.builder()
                        .id(uuid5)
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
