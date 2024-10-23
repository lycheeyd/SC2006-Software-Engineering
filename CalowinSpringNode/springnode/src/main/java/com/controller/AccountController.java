package com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.DataTransferObject.AuthDTO.ChangePasswordDTO;
import com.DataTransferObject.AuthDTO.ForgotPasswordDTO;
import com.DataTransferObject.AuthDTO.LoginDTO;
import com.DataTransferObject.AuthDTO.SignupDTO;


@RestController
@RequestMapping("/central/auth")
public class AccountController extends HttpReqController{

    public AccountController(RestTemplate restTemplate) {
        super(restTemplate);
    }

    // Implemenet you own mapping below

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupDTO user) {
        // Forward signup request to AuthModule
        String url = "http://localhost:8081/auth/signup"; // URL of Auth Java application
        return restTemplate.postForEntity(url, user, String.class);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO credentials) {
        // Forward login request to AuthModule
        String url = "http://localhost:8081/auth/login";
        return restTemplate.postForEntity(url, credentials, String.class);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO request) {
        // Forward change password request to AuthModule
        String url = "http://localhost:8081/auth/change-password";
        return restTemplate.postForEntity(url, request, String.class);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody ForgotPasswordDTO request) {
        // Forward forget password request to AuthModule
        String url = "http://localhost:8081/auth/forget-password";
        return restTemplate.postForEntity(url, request, String.class);
    }
}

