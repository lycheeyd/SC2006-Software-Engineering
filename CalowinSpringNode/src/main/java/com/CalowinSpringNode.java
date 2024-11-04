package com;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.config.CustomResponseErrorHandler;

import io.micrometer.common.lang.Nullable;


@SpringBootApplication
public class CalowinSpringNode {
    public static void main(String[] args) {
        SpringApplication.run(CalowinSpringNode.class, args);
    }

    // For forwarding https request
    @Bean
    public RestTemplate customRestTemplate(RestTemplateBuilder builder) {
        return builder.errorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                // Return false to prevent RestTemplate from throwing exceptions
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                // No handling here, just let the response be handled directly in the controller
            }
        }).build();
    }
/* 
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .errorHandler(new CustomResponseErrorHandler())
                .build();
    }
*/
}