package com.example.demo.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailsEntry {

    Long id;

    String title;

    String shortDescription;

    Double price;

    String type;

    String mission;

    List<String> tags;
}
