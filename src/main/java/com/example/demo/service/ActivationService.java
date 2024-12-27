package com.example.demo.service;

import com.example.demo.dto.activation.ActivationRequestDto;
import com.example.demo.dto.activation.ActivationResponseDto;

public interface ActivationService {

    ActivationResponseDto processKeyActivation(ActivationRequestDto keyActivationRequestDto);
}
