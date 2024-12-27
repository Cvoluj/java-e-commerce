package com.example.demo.service;

import com.example.demo.domain.CustomerDetails;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDetails> getAllCustomersDetails();

    CustomerDetails getCustomerDetailsById(UUID customerId);
}
