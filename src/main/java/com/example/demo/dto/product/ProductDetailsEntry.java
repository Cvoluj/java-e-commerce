package com.example.demo.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailsEntry {

    UUID id;

    String title;

    String shortDescription;

    Double price;

    String type;

    String mission;

    List<String> tags;
}
