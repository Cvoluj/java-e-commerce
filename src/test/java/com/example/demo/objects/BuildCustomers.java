package com.example.demo.objects;

import com.example.demo.domain.CustomerDetails;
import com.example.demo.dto.customer.CustomerDetailsDto;
import com.example.demo.common.MissionType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuildCustomers {

    public CustomerDetailsDto buildCustomerDetailsDto() {
        return CustomerDetailsDto.builder()
                .name("Mister Bisnes")
                .phoneNumber("+380631234567")
                .email("mr.bussines@example.com")
                .missionTypes(List.of(MissionType.EXPLORATION, MissionType.SCIENTIFIC))
                .build();
    }

    public CustomerDetails buildCustomerDetails() {
        return CustomerDetails.builder()
                .id(1L)
                .name("Mister Bisnes")
                .phoneNumber("+380631234567")
                .email("mr.bussines@example.com")
                .missionTypes(List.of(MissionType.EXPLORATION, MissionType.SCIENTIFIC))
                .build();
    }

    public CustomerDetails buildInvalidCustomerDetails() {
        return CustomerDetails.builder()
                .id(null)
                .name(" ")
                .phoneNumber("12345678910111213")
                .email("invalid-email")
                .missionTypes(List.of(MissionType.EXPLORATION))
                .build();
    }
}
