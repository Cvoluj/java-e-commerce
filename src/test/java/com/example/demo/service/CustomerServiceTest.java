package com.example.demo.service;

import com.example.demo.domain.CustomerDetails;
import com.example.demo.service.exception.CustomerNotFoundException;
import com.example.demo.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    @DisplayName("Should return customer details by ID")
    void shouldFindCustomerById() {
        UUID customerId = customerService.uuid1;
        CustomerDetails customer = CustomerDetails.builder()
                .id(customerId)
                .name("Jack Spring")
                .email("jackspring@gmail.com")
                .phoneNumber("+1234567890")
                .missionTypes(List.of())
                .build();


        CustomerDetails foundCustomer = customerService.getCustomerDetailsById(customerId);

        assertNotNull(foundCustomer, "Customer should not be null");
        assertEquals(customerId, foundCustomer.getId(), "Customer ID should match");
        assertEquals("Jack Spring", foundCustomer.getName(), "Customer name should match");
    }

    @Test
    @DisplayName("Should throw CustomerNotFoundException when customer not found by ID")
    void shouldThrowCustomerNotFoundException() {
        UUID invalidCustomerId = UUID.fromString("9149eb42-8f2c-442f-a192-8c1d9184127b");

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
                () -> customerService.getCustomerDetailsById(invalidCustomerId),
                "Expected CustomerNotFoundException to be thrown");

        assertEquals(String.format("Customer with ID %s not found", invalidCustomerId), exception.getMessage(),
                "Exception message should match the expected message");
    }
}
