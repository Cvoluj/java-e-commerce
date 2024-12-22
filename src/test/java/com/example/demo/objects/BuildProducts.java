package com.example.demo.objects;

import com.example.demo.domain.ProductDetails;
import com.example.demo.dto.product.ProductDetailsDto;
import com.example.demo.common.MissionType;
import com.example.demo.common.SpacecraftType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class BuildProducts {

    public ProductDetails buildProductDetails() {
        return ProductDetails.builder()
                .id(1L)
                .title("Spaceship Model A")
                .shortDescription("A highly advanced spaceship model for deep space exploration.")
                .price(99999.99)
                .type(SpacecraftType.SPACESHIP)
                .mission(MissionType.EXPLORATION)
                .tags(Arrays.asList("spaceship", "exploration", "advanced"))
                .build();
    }

    public ProductDetailsDto buildProductDetailsDto() {
        return ProductDetailsDto.builder()
                .id(1L)
                .title("Spaceship Model A")
                .shortDescription("A highly advanced spaceship model for deep space exploration.")
                .price(99999.99)
                .type(SpacecraftType.SPACESHIP.name())
                .mission(MissionType.EXPLORATION.name())
                .tags(Arrays.asList("spaceship", "exploration", "advanced"))
                .build();
    }

    public ProductDetails buildInvalidProductDetails() {
        return ProductDetails.builder()
                .id(999L)
                .title(" ")
                .shortDescription("Short description")
                .price(-50.00)
                .type(null)
                .mission(null)
                .tags(Arrays.asList(""))
                .build();
    }

    public List<ProductDetails> buildProductList() {
        return Arrays.asList(
                ProductDetails.builder()
                        .id(1L)
                        .title("Rocket Model X")
                        .shortDescription("A high-performance rocket for orbital missions.")
                        .price(79999.99)
                        .type(SpacecraftType.ROCKET)
                        .mission(MissionType.COMMERCIAL)
                        .tags(Arrays.asList("rocket", "orbital", "commercial"))
                        .build(),
                ProductDetails.builder()
                        .id(2L)
                        .title("Satellite Model Y")
                        .shortDescription("A satellite designed for long-term space observation.")
                        .price(34999.99)
                        .type(SpacecraftType.SATELLITE)
                        .mission(MissionType.SCIENTIFIC)
                        .tags(Arrays.asList("satellite", "space", "scientific"))
                        .build()
        );
    }
}
