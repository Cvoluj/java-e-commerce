package com.example.demo.objects;

import com.example.demo.domain.ProductDetails;
import com.example.demo.dto.product.ProductDetailsDto;
import com.example.demo.common.MissionType;
import com.example.demo.common.SpacecraftType;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class BuildProducts {
    private final static UUID uuid1 = UUID.fromString("24b5e9b6-5e18-4781-af42-9c00b7c3d65a");
    private final static UUID uuid2 = UUID.fromString("b1af1deb-6f7e-4094-9431-19d5f9ea6d3d");
    private final static UUID wrongUUID = UUID.fromString("620174b8-7ef6-4a58-93a5-6b29a7a07b22");

    public static ProductDetails buildProductDetails() {
        return ProductDetails.builder()
                .id(uuid1)
                .title("Spaceship Model A")
                .shortDescription("A highly advanced spaceship model for deep space exploration.")
                .price(99999.99)
                .type(SpacecraftType.SPACESHIP)
                .mission(MissionType.EXPLORATION)
                .tags(Arrays.asList("spaceship", "exploration", "advanced"))
                .build();
    }

    public static ProductDetailsDto buildProductDetailsDto() {
        return ProductDetailsDto.builder()
                .id(uuid1)
                .title("Spaceship Model A")
                .shortDescription("A highly advanced spaceship model for deep space exploration.")
                .price(99999.99)
                .type(SpacecraftType.SPACESHIP.name())
                .mission(MissionType.EXPLORATION.name())
                .tags(Arrays.asList("spaceship", "exploration", "advanced"))
                .build();
    }

    public static ProductDetails buildInvalidProductDetails() {
        return ProductDetails.builder()
                .id(wrongUUID)
                .title(" ")
                .shortDescription("Short description")
                .price(-50.00)
                .type(null)
                .mission(null)
                .tags(Arrays.asList(""))
                .build();
    }

    public static List<ProductDetails> buildProductList() {
        return Arrays.asList(
                ProductDetails.builder()
                        .id(uuid1)
                        .title("Rocket Model X")
                        .shortDescription("A high-performance rocket for orbital missions.")
                        .price(79999.99)
                        .type(SpacecraftType.ROCKET)
                        .mission(MissionType.COMMERCIAL)
                        .tags(Arrays.asList("rocket", "orbital", "commercial"))
                        .build(),
                ProductDetails.builder()
                        .id(uuid2)
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
