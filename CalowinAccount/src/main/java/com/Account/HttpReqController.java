package com.Account;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.DataTransferObject.ChangePasswordDTO;
import com.DataTransferObject.ForgotPasswordDTO;
import com.DataTransferObject.LoginDTO;
import com.DataTransferObject.ResponseDTO;
import com.DataTransferObject.SignupDTO;

@RestController
@RequestMapping("/account")
public class HttpReqController {

    @Autowired
    private AccountManagementService accountManagementService;

    @Autowired
    private PasswordManagementService passwordManagementService;

    @Autowired
    private ProfileManagementService profileManagementService;

    @Autowired
    private OTPService otpService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupDTO signupDTO) {
        // Signup logic (save user details to DB)
        try {
            ResponseDTO responseDTO = accountManagementService.signup(signupDTO.getEmail(), signupDTO.getPassword(), signupDTO.getConfirm_password(), signupDTO.getName(), signupDTO.getWeight());
    
            // Prepare response after successful signup
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Signup successful");
            response.put("UserObject", responseDTO);
    
            return ResponseEntity.ok(response);
    
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signup failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during signup: " + e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        // Login logic (check username/password)
        try {
            ResponseDTO responseDTO = accountManagementService.login(loginDTO.getEmail(), loginDTO.getPassword());

            // Prepare response after successful login
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("UserObject", responseDTO);
            
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            // Return unauthorized error for invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during login: " + e.getMessage());
        }
        
    }

    @PostMapping("/send-OTP")
    public ResponseEntity<String> sendOTP(String email) {
        try {
            // Generates the OTP
            String otpCode = otpService.generateAndSaveOTP(email);

            // NEED EMAIL SERVICE TO SEND OTP
            // implement next time after email service is setup
            return ResponseEntity.ok("OTP sent to email associate with the account");
        
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending OTP: " + e.getMessage());
        }

    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        // Change password logic (update password in DB)
        try {
            passwordManagementService.changePassword(changePasswordDTO.getUserID(), changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword(), changePasswordDTO.getConfirm_newPassword());
            
            return ResponseEntity.ok("Password changed successfully");

        } catch (RuntimeException e) {
            // Return unauthorized error for invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
        
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        // Forget password logic (send reset link)
        // implement next time after email service is setup
        return ResponseEntity.ok("Password reset link sent");
    }

    @PostMapping("/edit-profile")
    public ResponseEntity<String> editProfile(@RequestBody EditProfileDTO request) {
        // Edit account logic
        return ResponseEntity.ok("");
    }

    @PostMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(@RequestBody DeleteAccountDTO request) {
        // Delete account logic
        return ResponseEntity.ok("");
    }

}
