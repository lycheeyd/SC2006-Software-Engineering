package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CalowinSpringNode {
    public static void main(String[] args) {
        SpringApplication.run(CalowinSpringNode.class, args);
    }

    // For forwarding https request
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
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