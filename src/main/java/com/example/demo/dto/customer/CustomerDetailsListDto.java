package com.example.demo.dto.customer;

import lombok.extern.jackson.Jacksonized;
import lombok.Builder;
import lombok.Value;
import java.util.List;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class CustomerDetailsListDto {

    List<CustomerDetailsEntry> customerDetailsEntries;

}
