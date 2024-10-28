package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.WellnessZone" })
public class CalowinWellnessZone {
    public static void main(String[] args) {
        SpringApplication.run(CalowinWellnessZone.class, args);
    }
}