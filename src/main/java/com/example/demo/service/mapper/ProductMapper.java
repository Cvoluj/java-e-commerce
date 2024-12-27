package com.example.demo.service.mapper;

import com.example.demo.common.SpacecraftType;
import com.example.demo.common.MissionType;
import com.example.demo.domain.ProductDetails;
import com.example.demo.dto.product.ProductDetailsDto;
import com.example.demo.dto.product.ProductDetailsEntry;
import com.example.demo.dto.product.ProductDetailsListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "title", source = "title")
    @Mapping(target = "shortDescription", source = "shortDescription")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "type", source = "type", qualifiedByName = "toSpacecraftTypeString")
    @Mapping(target = "mission", source = "mission", qualifiedByName = "toMissionTypeString")
    ProductDetailsDto toProductDetailsDto(ProductDetails productDetails);

    default ProductDetailsListDto toProductDetailsListDto(List<ProductDetails> productDetailsList) {
        return ProductDetailsListDto.builder()
                .productDetailsEntries(toProductDetailsEntry(productDetailsList))
                .build();
    }

    List<ProductDetailsEntry> toProductDetailsEntry(List<ProductDetails> productDetails);

    @Mapping(target = "title", source = "title")
    @Mapping(target = "shortDescription", source = "shortDescription")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "type", source = "type", qualifiedByName = "toSpacecraftTypeFromString")
    @Mapping(target = "mission", source = "mission", qualifiedByName = "toMissionTypeFromString")
    ProductDetails toProductDetails(ProductDetailsDto product);

    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "title", source = "product.title")
    @Mapping(target = "shortDescription", source = "product.shortDescription")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "type", source = "product.type", qualifiedByName = "toSpacecraftTypeFromString")
    @Mapping(target = "mission", source = "product.mission", qualifiedByName = "toMissionTypeFromString")
    ProductDetails toProductDetails(UUID id, ProductDetailsDto product);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "shortDescription", source = "shortDescription")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "type", source = "type", qualifiedByName = "toSpacecraftTypeString")
    @Mapping(target = "mission", source = "mission", qualifiedByName = "toMissionTypeString")
    ProductDetailsEntry toProductDetailsEntry(ProductDetails productDetails);

    @Named("toSpacecraftTypeString")
    default String toSpacecraftTypeString(SpacecraftType spacecraftType) {
        if (spacecraftType == null) {
            return null;
        }
        return spacecraftType.getName().toLowerCase();
    }

    @Named("toMissionTypeString")
    default String toMissionTypeString(MissionType missionType) {
        if (missionType == null) {
            return null;
        }
        return missionType.getName().toLowerCase();
    }

    @Named("toSpacecraftTypeFromString")
    default SpacecraftType toSpacecraftTypeFromString(String spacecraftType) {
        return SpacecraftType.fromName(spacecraftType);
    }

    @Named("toMissionTypeFromString")
    default MissionType toMissionTypeFromString(String missionType) {
        return MissionType.fromName(missionType);
    }

}
