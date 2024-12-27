package com.example.demo.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.domain.CustomerDetails;
import com.example.demo.dto.customer.CustomerDetailsDto;
import com.example.demo.objects.BuildCustomers;
import com.example.demo.service.CustomerService;
import com.example.demo.service.exception.CustomerNotFoundException;
import com.example.demo.service.mapper.CustomerDetailsMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Customer Controller IT")
@Tag("customer-service")
public class CustomerControllerIT {

    public static final UUID ID = UUID.fromString("606f8650-524b-44ce-8014-a0be9b4729be");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private CustomerDetailsMapper customerDetailsMapper;

    @Test
    @SneakyThrows
    void shouldThrowUnsupportedOperationExceptuinOnCreateCustomer() throws JsonProcessingException, Exception {
        CustomerDetailsDto customerDetailsDto = BuildCustomers.buildCustomerDetailsDto();

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(customerDetailsDto)))
                .andExpect(status().isNotImplemented())
                .andExpect(content().string("Client creation is not yet supported."));
    }

    @Test
    @SneakyThrows
    void shouldThrowsValidationExceptionWithNoValidationCustomerFields() throws JsonProcessingException, Exception {
        CustomerDetailsDto dto = customerDetailsMapper.toCustomerDetailsDto(BuildCustomers.buildInvalidCustomerDetails());

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("urn:problem-type:validation-error"))
                .andExpect(jsonPath("$.title").value("Field Validation Exception"))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.detail").value("Request validation failed"))
                .andExpect(jsonPath("$.invalidParams", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.invalidParams[*].fieldName")
                        .value(containsInAnyOrder("email", "phoneNumber", "name")))
                .andExpect(jsonPath("$.invalidParams[*].reason").exists());
    }


    @Test
    @SneakyThrows
    void shouldFindByIdCustomer() throws JsonProcessingException, Exception {
        CustomerDetails customer = BuildCustomers.buildCustomerDetails();

        when(customerService.getCustomerDetailsById(ID)).thenReturn(customer);

        mockMvc.perform(get("/api/v1/customers/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value(customer.getName()))
                .andExpect(jsonPath("$.email").value(customer.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(customer.getPhoneNumber()))
                .andExpect(jsonPath("$.missionTypes").isArray());

        verify(customerService, times(1)).getCustomerDetailsById(ID);
    }

    @Test
    @SneakyThrows
    void shouldThrowsCustomerNotFoundException() throws JsonProcessingException, Exception {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(NOT_FOUND, String.format("Customer with ID %s not found", ID));

        problemDetail.setType(URI.create("customer-not-found"));
        problemDetail.setTitle("Customer Not Found");

        doThrow(new CustomerNotFoundException(ID))
                .when(customerService).getCustomerDetailsById(ID);

        mockMvc.perform(get("/api/v1/customers/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(problemDetail)));

        assertThrows(CustomerNotFoundException.class, () -> {
            customerController.getCustomerById(ID);
        });

        verify(customerService, times(2)).getCustomerDetailsById(ID);
    }
}
