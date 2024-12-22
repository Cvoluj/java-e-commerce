package com.example.demo.dto.spacecraft;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpacecraftDetailsDto {

    @NotBlank
    @Size(max = 100, message = "Name must be at most 100 characters long.")
    String name;

    @NotBlank
    @Size(max = 366, message = "Description must be at most 366 characters long.")
    String description;

}
