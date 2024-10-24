package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.Account", "com.DataTransferObject", "com.Database"})
public class CalowinAccount {
    public static void main(String[] args) {
        SpringApplication.run(CalowinAccount.class, args);
    }
}