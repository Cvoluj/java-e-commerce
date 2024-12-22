package com.example.demo.service.impl;

import com.example.demo.common.MissionType;
import com.example.demo.domain.CustomerDetails;
import com.example.demo.service.exception.CustomerNotFoundException;
import com.example.demo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final List<CustomerDetails> customers = buildCustomerDetailsMock();

    @Override
    public List<CustomerDetails> getAllCustomersDetails() {
        log.info("Fetching all customer details");
        return new ArrayList<>(customers);
    }

    @Override
    public CustomerDetails getCustomerDetailsById(Long customerId) {
        log.info("Fetching customer details for ID: {}", customerId);
        return customers.stream()
                .filter(customer -> customer.getId().equals(customerId))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Customer with ID {} not found", customerId);
                    return new CustomerNotFoundException(customerId);
                });
    }

    private List<CustomerDetails> buildCustomerDetailsMock() {
        return List.of(
                CustomerDetails.builder()
                        .id(1L)
                        .name("Jack Spring")
                        .email("jacksrping@gmail.com")
                        .phoneNumber("+1234567890")
                        .region("Ukraine")
                        .missionTypes(List.of(MissionType.COMMERCIAL))
                        .build(),
                CustomerDetails.builder()
                        .id(2L)
                        .name("Emma Johnson")
                        .email("emma.johnson@example.com")
                        .phoneNumber("+1234567891")
                        .region("USA")
                        .missionTypes(List.of(MissionType.SCIENTIFIC))
                        .build(),
                CustomerDetails.builder()
                        .id(3L)
                        .name("Liam Smith")
                        .email("liam.smith@example.com")
                        .phoneNumber("+1234567892")
                        .region("UK")
                        .missionTypes(List.of(MissionType.MILITARY)
)                        .build(),
                CustomerDetails.builder()
                        .id(4L)
                        .name("Olivia Brown")
                        .email("olivia.brown@example.com")
                        .phoneNumber("+1234567893")
                        .region("Canada")
                        .missionTypes(List.of(MissionType.EXPLORATION))
                        .build(),
                CustomerDetails.builder()
                        .id(5L)
                        .name("Noah Wilson")
                        .email("noah.wilson@example.com")
                        .phoneNumber("+1234567894")
                        .region("Australia")
                        .missionTypes(List.of(MissionType.COMMERCIAL))
                        .build()
        );
    }
}
