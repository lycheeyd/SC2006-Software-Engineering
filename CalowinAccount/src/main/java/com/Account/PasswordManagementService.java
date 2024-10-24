package com.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.Database.CalowinSecureDBRepository;

import org.apache.commons.lang3.RandomStringUtils;


@Service
public class PasswordManagementService {

    @Autowired
    @Qualifier("calowinSecureDBTransactionManager")
    private PlatformTransactionManager calowinSecureDBTransactionManager;

    @Autowired
    private CalowinSecureDBRepository calowinSecureDBRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String SECRET_KEY = "ASK RAPHEL FOR KEY"; // Should be a 16/32-byte key

    // Change password method
    public void changePassword(String userid, String oldPassword, String newPassword, String confirmNewPassword) throws Exception {
        // Check if user exist
        UserEntity user = calowinSecureDBRepository.findByUserID(userid)
                .orElseThrow(() -> new RuntimeException("Invalid user"));

        String decryptedNewPassword = Decryptor.decrypt(newPassword, SECRET_KEY);
        String decryptedConfirmNewPassword = Decryptor.decrypt(confirmNewPassword, SECRET_KEY);
        String decryptedOldPassword = Decryptor.decrypt(oldPassword, SECRET_KEY);

        // Check if new password meet requirements
        isPasswordValid(decryptedNewPassword, decryptedConfirmNewPassword);

        // Authenticate old password
        if (!passwordEncoder.matches(decryptedOldPassword, user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }
        
        // Update new password into database (CALOWIN_SECURE) 
        user.setPassword(passwordEncoder.encode(decryptedNewPassword));
        calowinSecureDBRepository.save(user);

    }

    // Forgot password method
    public void forgotPassword(String email) throws Exception {
        // Forgot password logic
        String newPassword = RandomStringUtils.randomAlphanumeric(12);
        // send new password to email
        // implement next time after email service is setup
    }
    
    // Utility function to validate the password
    protected static void isPasswordValid(String password, String confirmPassword) throws Exception {
        // At least 8 characters, 1 uppercase, and 1 special character
        String passwordPattern = "^(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=.{8,}).*$";
        
        if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Password does not match");
        }

        if (!password.matches(passwordPattern)) {
            throw new RuntimeException("Invalid password. Password must have at least 8 characters, 1 uppercase, and 1 special character");
        }
    }

}
