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
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    public final UUID uuid1 = UUID.fromString("606f8650-524b-44ce-8014-a0be9b4729be");
    private final UUID uuid2 = UUID.fromString("de7e98b7-0072-41d6-ac46-21578c3c28c7");
    private final UUID uuid3 = UUID.fromString("026a362f-91b6-43fb-bbbf-4e299a864a9f");
    private final UUID uuid4 = UUID.fromString("3f0d8070-19d1-4e7b-9313-79e26054b2a9");
    private final UUID uuid5 = UUID.fromString("05d13fc5-06db-4337-a614-79e1d13f073e");
    private final List<CustomerDetails> customers = buildCustomerDetailsMock();

    @Override
    public List<CustomerDetails> getAllCustomersDetails() {
        log.info("Fetching all customer details");
        return new ArrayList<>(customers);
    }

    @Override
    public CustomerDetails getCustomerDetailsById(UUID customerId) {
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
                        .id(uuid1)
                        .name("Jack Spring")
                        .email("jacksrping@gmail.com")
                        .phoneNumber("+1234567890")
                        .region("Ukraine")
                        .missionTypes(List.of(MissionType.COMMERCIAL))
                        .build(),
                CustomerDetails.builder()
                        .id(uuid2)
                        .name("Emma Parkinson")
                        .email("emma.parkinson@example.com")
                        .phoneNumber("+1234567891")
                        .region("USA")
                        .missionTypes(List.of(MissionType.SCIENTIFIC))
                        .build(),
                CustomerDetails.builder()
                        .id(uuid3)
                        .name("Liam Smith")
                        .email("liam.smith@example.com")
                        .phoneNumber("+1234567892")
                        .region("UK")
                        .missionTypes(List.of(MissionType.MILITARY)
)                        .build(),
                CustomerDetails.builder()
                        .id(uuid4)
                        .name("Olivia Brown")
                        .email("olivia.brown@example.com")
                        .phoneNumber("+1234567893")
                        .region("Canada")
                        .missionTypes(List.of(MissionType.EXPLORATION))
                        .build(),
                CustomerDetails.builder()
                        .id(uuid5)
                        .name("Noah Wilson")
                        .email("noah.wilson@example.com")
                        .phoneNumber("+1234567894")
                        .region("Australia")
                        .missionTypes(List.of(MissionType.COMMERCIAL))
                        .build()
        );
    }
}
