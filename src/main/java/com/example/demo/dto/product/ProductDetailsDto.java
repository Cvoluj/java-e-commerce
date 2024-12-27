package com.example.demo.dto.product;

import com.example.demo.dto.validation.ExtendValidation;
import com.example.demo.dto.validation.annotation.ValidMissionType;
import com.example.demo.dto.validation.annotation.ValidSpacecraftType;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.UUID;

@Value
@Builder
@GroupSequence({ProductDetailsDto.class, ExtendValidation.class})
@Jacksonized
public class ProductDetailsDto {

    @NotNull(message = "ID cannot be null")
    UUID id;

    @NotBlank(message = "Title cannot be blank.")
    @Size(max = 100, message = "Title must not exceed 100 characters.")
    String title;

    @NotBlank(message = "Short description cannot be blank.")
    @Size(max = 500, message = "Short description must not exceed 500 characters.")
    String shortDescription;

    @NotNull(message = "Price cannot be null.")
    @Min(value = 0, message = "Price must be greater than or equal to 0.")
    Double price;

    @ValidSpacecraftType
    @NotBlank(message = "Spacecraft type is required.")
    String type;

    @ValidMissionType
    @NotBlank(message = "Mission type is required.")
    String mission;

    @Size(max = 10, message = "Tags list must not exceed 10 items.")
    List<@NotBlank(message = "Tag cannot be blank.") String> tags;
}
