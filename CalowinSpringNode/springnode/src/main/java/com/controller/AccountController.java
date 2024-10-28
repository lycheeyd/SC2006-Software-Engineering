package com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.DataTransferObject.AccountDTO.ChangePasswordDTO;
import com.DataTransferObject.AccountDTO.DeleteAccountDTO;
import com.DataTransferObject.AccountDTO.EditProfileDTO;
import com.DataTransferObject.AccountDTO.ForgotPasswordDTO;
import com.DataTransferObject.AccountDTO.LoginDTO;
import com.DataTransferObject.AccountDTO.SendOtpDTO;
import com.DataTransferObject.AccountDTO.SignupDTO;

@RestController
@RequestMapping("/central/account")
public class AccountController extends HttpReqController{

    public AccountController(RestTemplate restTemplate) {
        super(restTemplate);
    }

    // Implemenet your own mapping below

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupDTO DTO) {
        // Forward signup request to AccountModule
        String url = "http://localhost:8081/account/signup"; // URL of Account Java application
        return restTemplate.postForEntity(url, DTO, String.class);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO DTO) {
        // Forward login request to AccountModule
        String url = "http://localhost:8081/account/login";
        return restTemplate.postForEntity(url, DTO, String.class);
    }

    @PostMapping("/send-OTP")
    public ResponseEntity<?> login(@RequestBody SendOtpDTO DTO) {
        String url = "http://localhost:8081/account/send-OTP";
        return restTemplate.postForEntity(url, DTO, String.class);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO DTO) {
        // Forward change password request to AccountModule
        String url = "http://localhost:8081/account/change-password";
        return restTemplate.postForEntity(url, DTO, String.class);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestBody ForgotPasswordDTO DTO) {
        // Forward forget password request to AccountModule
        String url = "http://localhost:8081/account/forget-password";
        return restTemplate.postForEntity(url, DTO, String.class);
    }

    @PostMapping("/edit-profile")
    public ResponseEntity<?> editProfile(@RequestBody EditProfileDTO DTO) {
        // Forward edit profile request to AccountModule
        String url = "http://localhost:8081/account/edit-profile";
        return restTemplate.postForEntity(url, DTO, String.class);
    }

    @PostMapping("/delete-account")
    public ResponseEntity<?> deleteAccount(@RequestBody DeleteAccountDTO DTO) {
        // Forward delete account request to AccountModule
        String url = "http://localhost:8081/account/delete-account";
        return restTemplate.postForEntity(url, DTO, String.class);
    }

    @GetMapping("/view-profile/{userID}")
    public ResponseEntity<?> viewProfile(@PathVariable String userID) {
        // Forward view profile request to AccountModule
        String url = "http://localhost:8081/account/view-profile/" + userID;
        return restTemplate.getForEntity(url, String.class);
    }
    
}

