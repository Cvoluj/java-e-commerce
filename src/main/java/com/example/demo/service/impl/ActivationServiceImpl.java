package com.example.demo.service.impl;

import com.example.demo.dto.activation.ActivationRequestDto;
import com.example.demo.dto.activation.ActivationResponseDto;
import com.example.demo.service.ActivationService;
import com.example.demo.service.exception.FailedProcessActivation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ActivationServiceImpl implements ActivationService {

    private final RestTemplate keyClient;
    private final String keyServiceEndpoint;

    public ActivationServiceImpl(@Qualifier("keyRestClient") RestTemplate keyClient,
                                 @Value("${application.key-service.keys}") String keyServiceEndpoint) {
        this.keyClient = keyClient;
        this.keyServiceEndpoint = keyServiceEndpoint;
    }

    @Override
    public ActivationResponseDto processKeyActivation(ActivationRequestDto key) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ActivationRequestDto> requestEntity = new HttpEntity<>(key, headers);

        try {
            ActivationResponseDto response = keyClient.postForObject(keyServiceEndpoint, requestEntity, ActivationResponseDto.class);

            if (response == null) {
                throw new FailedProcessActivation("Received null response from key activation service");
            }

            log.info("Key activation successful: {}", response);
            return response;

        } catch (Exception e) {
            log.error("Error during key activation: {}", e.getMessage());
            throw new FailedProcessActivation("Error during key activation process");
        }
    }
}

