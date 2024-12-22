package com.example.demo.dto.customer;

import com.example.demo.common.MissionType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Value
@Builder
@Jacksonized
@Validated
public class CustomerDetailsDto {

    @NotBlank(message = "Ім'я клієнта не може бути порожнім.")
    @Size(max = 100, message = "Ім'я клієнта не повинно перевищувати 100 символів.")
    String name;

    @NotBlank(message = "Номер телефону не може бути порожнім.")
    @Size(max = 15, message = "Номер телефону не повинен перевищувати 15 символів.")
    String phoneNumber;

    @Email(message = "Неправильний формат електронної пошти.")
    @NotBlank(message = "Електронна пошта не може бути порожньою.")
    String email;

    @NotEmpty(message = "Список типів місій не може бути порожнім.")
    List<MissionType> missionTypes;
}
