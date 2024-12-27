package com.example.demo.objects;

import com.example.demo.domain.CustomerDetails;
import com.example.demo.dto.customer.CustomerDetailsDto;
import com.example.demo.common.MissionType;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@UtilityClass
public class BuildCustomers {
    static UUID uuid1 = UUID.fromString("61013972-48a9-4040-9c2b-06fc6c9ff80a");

    public static CustomerDetailsDto buildCustomerDetailsDto() {
        return CustomerDetailsDto.builder()
                .name("Mister Bisnes")
                .phoneNumber("+380631234567")
                .email("mr.bussines@example.com")
                .missionTypes(List.of(MissionType.EXPLORATION, MissionType.SCIENTIFIC))
                .build();
    }

    public static CustomerDetails buildCustomerDetails() {
        return CustomerDetails.builder()
                .id(uuid1)
                .name("Mister Bisnes")
                .phoneNumber("+380631234567")
                .email("mr.bussines@example.com")
                .missionTypes(List.of(MissionType.EXPLORATION, MissionType.SCIENTIFIC))
                .build();
    }

    public static CustomerDetails buildInvalidCustomerDetails() {
        return CustomerDetails.builder()
                .id(null)
                .name(" ")
                .phoneNumber("12345678910111213")
                .email("invalid-email")
                .missionTypes(List.of(MissionType.EXPLORATION))
                .build();
    }
}
