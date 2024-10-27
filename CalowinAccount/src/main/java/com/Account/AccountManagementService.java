package com.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.DataTransferObject.LoginResponseDTO;
import com.Database.CalowinDB.UserInfoRepository;
import com.Database.CalowinSecureDB.SecureInfoDBRepository;

import org.apache.commons.lang3.RandomStringUtils;


@Service
public class AccountManagementService {

    @Autowired
    @Qualifier("calowinSecureDBTransactionManager")
    private PlatformTransactionManager calowinSecureDBTransactionManager;

    @Autowired
    @Qualifier("calowinDBTransactionManager")
    private PlatformTransactionManager calowinDBTransactionManager;

    @Autowired
    private SecureInfoDBRepository calowinSecureDBRepository;

    @Autowired
    private UserInfoRepository calowinDBRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordSecurityService passwordSecurityService;

    @Autowired
    private OTPService otpService;

    private static final String SECRET_KEY = "ASK RAPHEL FOR KEY"; // Should be a 16/32-byte key

    // Signup method
    @Transactional //(transactionManager = "calowinSecureDBTransactionManager")
    public LoginResponseDTO signup(String email, String encryptedPassword, String encryptedConfirmPassword, String name, float weight, String otpCode) throws Exception {
        // Authenticate OTP
        if (!otpService.verifyOTP(email, otpCode)) {
            throw new RuntimeException("Incorrect OTP");
        }
        
        // Check if user exist
        if (calowinSecureDBRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        String decryptedPassword = passwordSecurityService.decrypt(encryptedPassword, SECRET_KEY);
        String decryptedConfirmPassword = passwordSecurityService.decrypt(encryptedConfirmPassword, SECRET_KEY);

        // Check if password meet requirements
        passwordSecurityService.isPasswordValid(decryptedPassword, decryptedConfirmPassword);
        //passwordSecurityService.isPasswordValid(encryptedPassword, encryptedConfirmPassword);
        // Generate userID
        String userID = generateUniqueUserId();

        // Create and store user credentials in database (CALOWIN_SECURE)
        UserEntity user = new UserEntity(userID, email, passwordEncoder.encode(encryptedConfirmPassword));
        calowinSecureDBRepository.save(user);

        // Create and store user info in database (CALOWIN)
        ProfileEntity profile = new ProfileEntity(userID, name, weight, "");
        calowinDBRepository.save(profile);

        // Prepare and returns user data to frontend
        return new LoginResponseDTO(user.getUserID(), user.getEmail(), profile.getName(), profile.getWeight(), profile.getBio());

    }

    // Login method
    public LoginResponseDTO login(String email, String encryptedPassword) throws Exception {
        String decryptedPassword = passwordSecurityService.decrypt(encryptedPassword, SECRET_KEY);

        UserEntity user = calowinSecureDBRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(decryptedPassword, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        ProfileEntity profile = calowinDBRepository.findByUserID(user.getUserID())
        .orElseThrow(() -> new RuntimeException("Failed to retrieve userdata"));;

        return new LoginResponseDTO(user.getUserID(), user.getEmail(), profile.getName(), profile.getWeight(), profile.getBio());
    
    }

    // Delete account method
    @Transactional //(transactionManager = "CalowinSecureDBTransactionManager")
    public void deleteAccount(String userID, int OTP) throws Exception {
        // Authenticate OTP
        //if (!otpService.verifyOTP(email, otpCode) {
        //    throw new RuntimeException("Incorrect OTP");
        //}

        // delete account logic
        // implement next time after email service is setup
        
    }

    // Method to generate a unique 8-character userID
    private String generateUniqueUserId() {
        String userID;
        boolean exists;
    
        // Loop until a unique userID is generated
        do {
            // Generate random 8-character alphanumeric string (both letters and numbers)
            userID = RandomStringUtils.randomAlphanumeric(8).toUpperCase();;
            // Check if the generated userID already exists in the database
            exists = calowinSecureDBRepository.existsByUserID(userID);
        } while (exists);
    
        return userID;
    }

}
