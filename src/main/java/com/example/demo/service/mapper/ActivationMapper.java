package com.example.demo.service.mapper;

import com.example.demo.dto.activation.ActivationRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ActivationMapper {

    ActivationMapper INSTANCE = Mappers.getMapper(ActivationMapper.class);

    @Mapping(source = "key", target = "key")
    @Mapping(source = "customerId", target = "customerId")
    @Mapping(source = "status", target = "status")
    ActivationRequestDto toKeyContext(String key, String customerId, String status);
}
