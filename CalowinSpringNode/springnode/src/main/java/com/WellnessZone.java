package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.WellnessZone" })
public class WellnessZone {
    public static void main(String[] args) {
        SpringApplication.run(WellnessZone.class, args);
    }
}