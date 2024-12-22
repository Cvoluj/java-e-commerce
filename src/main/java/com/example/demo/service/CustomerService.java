package com.example.demo.service;

import com.example.demo.domain.CustomerDetails;

import java.util.List;

public interface CustomerService {

    List<CustomerDetails> getAllCustomersDetails();

    CustomerDetails getCustomerDetailsById(Long customerId);
}
