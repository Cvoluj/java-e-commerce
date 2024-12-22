package com.example.demo.service;

import com.example.demo.domain.CustomerDetails;
import com.example.demo.service.exception.CustomerNotFoundException;
import com.example.demo.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private List<CustomerDetails> customerDetailsList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return customer details by ID")
    void shouldFindCustomerById() {
        Long customerId = 1L;
        CustomerDetails customer = CustomerDetails.builder()
                .id(customerId)
                .name("Jack Spring")
                .email("jackspring@gmail.com")
                .phoneNumber("+1234567890")
                .missionTypes(List.of())
                .build();

        when(customerDetailsList.stream()).thenReturn(List.of(customer).stream());

        CustomerDetails foundCustomer = customerService.getCustomerDetailsById(customerId);

        assertNotNull(foundCustomer, "Customer should not be null");
        assertEquals(customerId, foundCustomer.getId(), "Customer ID should match");
        assertEquals("Jack Spring", foundCustomer.getName(), "Customer name should match");
    }

    @Test
    @DisplayName("Should throw CustomerNotFoundException when customer not found by ID")
    void shouldThrowCustomerNotFoundException() {
        Long invalidCustomerId = 999L;

        Stream<CustomerDetails> mockStream = mock(Stream.class);

        when(customerDetailsList.stream()).thenReturn(mockStream);
        when(mockStream.filter(customer -> customer.getId().equals(invalidCustomerId)))
                .thenReturn(mockStream);
        when(mockStream.findFirst()).thenReturn(Optional.empty());

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
                () -> customerService.getCustomerDetailsById(invalidCustomerId),
                "Expected CustomerNotFoundException to be thrown");

        assertEquals("Customer with ID 999 not found", exception.getMessage(),
                "Exception message should match the expected message");
    }
}
