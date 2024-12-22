package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Configuration
public class ClientConfiguration {

    private final int responseTimeout;

    public ClientConfiguration() {
        this.responseTimeout = 1000;
        log.info("RestClient configured with response timeout: {} ms", responseTimeout);
    }

    @Bean("keyRestClient")
    public RestTemplate keyRestClient() {
        log.info("Creating RestClient bean with response timeout: {} ms", responseTimeout);
        return new RestTemplate(getClientHttpRequestFactory());
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(responseTimeout);
        factory.setConnectTimeout(responseTimeout);
        return factory;
    }
}
