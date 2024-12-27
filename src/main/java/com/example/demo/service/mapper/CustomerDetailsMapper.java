package com.example.demo.service.mapper;

import com.example.demo.common.MissionType;
import com.example.demo.domain.CustomerDetails;
import com.example.demo.dto.customer.CustomerDetailsDto;
import com.example.demo.dto.customer.CustomerDetailsEntry;
import com.example.demo.dto.customer.CustomerDetailsListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerDetailsMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "missionTypes", source = "missionTypes", qualifiedByName = "mapMissionTypeList")
    CustomerDetailsDto toCustomerDetailsDto(CustomerDetails customerDetails);

    default CustomerDetailsListDto toCustomerDetailsListDto(List<CustomerDetails> customerDetails) {
        return CustomerDetailsListDto.builder()
                .customerDetailsEntries(toCustomerDetailsEntry(customerDetails))
                .build();
    }

    List<CustomerDetailsEntry> toCustomerDetailsEntry(List<CustomerDetails> customerDetails);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "missionTypes", source = "missionTypes", qualifiedByName = "mapMissionTypeList")
    CustomerDetailsEntry toCustomerDetailsEntry(CustomerDetails customerDetails);

    @Named("mapMissionTypeList")
    default List<MissionType> mapMissionTypeList(List<MissionType> missionTypes) {
        return missionTypes;
    }

}
