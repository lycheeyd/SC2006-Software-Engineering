package com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.DataTransferObject.AuthDTO.ChangePasswordDTO;
import com.DataTransferObject.AuthDTO.DeleteAccountDTO;
import com.DataTransferObject.AuthDTO.EditProfileDTO;
import com.DataTransferObject.AuthDTO.ForgotPasswordDTO;
import com.DataTransferObject.AuthDTO.LoginDTO;
import com.DataTransferObject.AuthDTO.SignupDTO;


@RestController
@RequestMapping("/central/account")
public class AccountController extends HttpReqController{

    public AccountController(RestTemplate restTemplate) {
        super(restTemplate);
    }

    // Implemenet you own mapping below

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupDTO request) {
        // Forward signup request to AccountModule
        String url = "http://localhost:8081/account/signup"; // URL of Auth Java application
        return restTemplate.postForEntity(url, request, String.class);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO request) {
        // Forward login request to AccountModule
        String url = "http://localhost:8081/account/login";
        return restTemplate.postForEntity(url, request, String.class);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO request) {
        // Forward change password request to AccountModule
        String url = "http://localhost:8081/account/change-password";
        return restTemplate.postForEntity(url, request, String.class);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody ForgotPasswordDTO request) {
        // Forward forget password request to AccountModule
        String url = "http://localhost:8081/account/forget-password";
        return restTemplate.postForEntity(url, request, String.class);
    }

    @PostMapping("/edit-profile")
    public ResponseEntity<String> editProfile(@RequestBody EditProfileDTO request) {
        // Forward forget password request to AccountModule
        String url = "http://localhost:8081/account/edit-profile";
        return restTemplate.postForEntity(url, request, String.class);
    }

    @PostMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(@RequestBody DeleteAccountDTO request) {
        // Forward forget password request to AccountModule
        String url = "http://localhost:8081/account/delete-account";
        return restTemplate.postForEntity(url, request, String.class);
    }
}

