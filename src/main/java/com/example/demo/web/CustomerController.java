package com.example.demo.web;

import com.example.demo.dto.customer.CustomerDetailsDto;
import com.example.demo.dto.customer.CustomerDetailsListDto;
import com.example.demo.service.CustomerService;
import com.example.demo.service.mapper.CustomerDetailsMapper;

import com.example.demo.web.exception.CustomValidationException;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerDetailsMapper customerDetailsMapper;

    public CustomerController(CustomerService customerService, CustomerDetailsMapper customerDetailsMapper) {
        this.customerService = customerService;
        this.customerDetailsMapper = customerDetailsMapper;
    }

    @GetMapping
    public ResponseEntity<CustomerDetailsListDto> getAllCustomers() {
        var customerDetailsList = customerService.getAllCustomersDetails();
        var customerDetailsListDto = customerDetailsMapper.toCustomerDetailsListDto(customerDetailsList);
        return ResponseEntity.ok(customerDetailsListDto);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDetailsDto> getCustomerById(@PathVariable UUID customerId) {
        var customerDetails = customerService.getCustomerDetailsById(customerId);
        var customerDetailsDto = customerDetailsMapper.toCustomerDetailsDto(customerDetails);
        return ResponseEntity.ok(customerDetailsDto);
    }


    @PostMapping
    public ResponseEntity<Void> createCustomer(
            @RequestBody @Valid CustomerDetailsDto customerDetailsDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<CustomValidationException.ValidationError> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> new CustomValidationException.ValidationError(
                            error.getField(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());

            throw new CustomValidationException("Validation failed for input data", errors);
        }

        throw new UnsupportedOperationException("Client creation is not yet supported.");
    }


    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<Object> handleCustomValidationException(CustomValidationException ex) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("type", "urn:problem-type:validation-error");
        response.put("title", "Field Validation Exception");
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("detail", "Request validation failed");
        response.put("invalidParams", ex.getErrors());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }



    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<String> handleUnsupportedOperationException(UnsupportedOperationException ex) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(ex.getMessage());
    }
}
