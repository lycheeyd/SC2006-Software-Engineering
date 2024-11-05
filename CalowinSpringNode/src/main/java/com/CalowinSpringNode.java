package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;

import com.config.CustomRestTemplate;



@SpringBootApplication
public class CalowinSpringNode {
    public static void main(String[] args) {
        SpringApplication.run(CalowinSpringNode.class, args);
    }

    // For forwarding https request
    @Bean
    public CustomRestTemplate customRestTemplate(RestTemplateBuilder builder) {
        return new CustomRestTemplate(builder);
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