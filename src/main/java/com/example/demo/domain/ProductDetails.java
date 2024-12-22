package com.example.demo.domain;

import com.example.demo.common.SpacecraftType;
import com.example.demo.common.MissionType;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class ProductDetails {
    Long id;
    String title;
    String shortDescription;
    Double price;
    SpacecraftType type;
    MissionType mission;
    List<String> tags;
}
