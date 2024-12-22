package com.example.demo.domain;

import com.example.demo.common.MissionType;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CustomerDetails {

    Long id;
    String name;
    String phoneNumber;
    String email;
    String region;
    List<MissionType> missionTypes;
}
