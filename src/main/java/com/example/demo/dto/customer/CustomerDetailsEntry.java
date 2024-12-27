package com.example.demo.dto.customer;

import com.example.demo.common.MissionType;
import lombok.extern.jackson.Jacksonized;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class CustomerDetailsEntry {

    UUID id;
    String name;
    String phoneNumber;
    String email;
    String region;
    List<MissionType> missionTypes;
}
