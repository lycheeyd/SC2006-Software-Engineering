package com.Account.Managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.Account.Entities.EmailType;
import com.Account.Entities.UserEntity;
import com.Account.Services.EmailService;
import com.Account.Services.OTPService;
import com.Account.Services.PasswordSecurityService;
import com.Database.CalowinSecureDB.SecureInfoDBRepository;

@Service
public class PasswordManagementService {

    @Autowired
    @Qualifier("calowinSecureDBTransactionManager")
    private PlatformTransactionManager calowinSecureDBTransactionManager;

    @Autowired
    private SecureInfoDBRepository calowinSecureDBRepository;

    @Autowired
    private PasswordSecurityService passwordSecurityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OTPService otpService;

    @Autowired
    private EmailService emailService;

    private static final String SECRET_KEY = "ASK RAPHEL FOR KEY"; // Should be a 16/32-byte key

    // Change password method
    public void changePassword(String userid, String oldPassword, String newPassword, String confirmNewPassword) throws Exception {
        // Check if user exist
        UserEntity user = calowinSecureDBRepository.findByUserID(userid)
                .orElseThrow(() -> new RuntimeException("Invalid user"));

        String decryptedNewPassword = passwordSecurityService.decrypt(newPassword, SECRET_KEY);
        String decryptedConfirmNewPassword = passwordSecurityService.decrypt(confirmNewPassword, SECRET_KEY);
        String decryptedOldPassword = passwordSecurityService.decrypt(oldPassword, SECRET_KEY);

        // Check if new password meet requirements
        passwordSecurityService.isPasswordValid(decryptedNewPassword, decryptedConfirmNewPassword);

        // Authenticate old password
        if (!passwordEncoder.matches(decryptedOldPassword, user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }
        
        // Update new password into database (CALOWIN_SECURE) 
        user.setPassword(passwordEncoder.encode(decryptedNewPassword));
        calowinSecureDBRepository.save(user);

    }

    // Forgot password method
    public void forgotPassword(String email, String otpCode) throws Exception {
        // Authenticate OTP
        if (!otpService.verifyOTP(email, otpCode)) {
            throw new RuntimeException("Invalid OTP");
        }

        String newPassword = passwordSecurityService.generateRandomPassword();
        
        // Send new password to email
        EmailType type = EmailType.SEND_NEW_PASSWORD;
        emailService.sendEmail(email, type.getSubject(), type.getMessageBody(newPassword));

    }

}
