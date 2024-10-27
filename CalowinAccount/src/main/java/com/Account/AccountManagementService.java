package com.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.Account.SecurityUtilities.Decryptor;
import com.Account.SecurityUtilities.PasswordValidator;
import com.DataTransferObject.LoginResponseDTO;
import com.Database.CalowinDB.CalowinDBRepository;
import com.Database.CalowinDB.FriendRelationshipRepository;
import com.Database.CalowinSecureDB.CalowinSecureDBRepository;
//import com.Database.CalowinDB.UserRepository;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

@Service
public class AccountManagementService {

    @Autowired
    @Qualifier("calowinSecureDBTransactionManager")
    private PlatformTransactionManager calowinSecureDBTransactionManager;

    @Autowired
    @Qualifier("calowinDBTransactionManager")
    private PlatformTransactionManager calowinDBTransactionManager;

    @Autowired
    private CalowinSecureDBRepository calowinSecureDBRepository;

    @Autowired
    private CalowinDBRepository calowinDBRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OTPService otpService;

    @Autowired
    private FriendRelationshipRepository friendRelationshipRepository;

   

    private static final String SECRET_KEY = "ASK RAPHEL FOR KEY"; // Replace with actual key

    // Signup method
    @Transactional(transactionManager = "calowinSecureDBTransactionManager")
    public LoginResponseDTO signup(String email, String encryptedPassword, String encryptedConfirmPassword, String name, float weight) throws Exception {
        
        // Check if user exists
        if (calowinSecureDBRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        String decryptedPassword = Decryptor.decrypt(encryptedPassword, SECRET_KEY);
        String decryptedConfirmPassword = Decryptor.decrypt(encryptedConfirmPassword, SECRET_KEY);

        // Validate password requirements
        PasswordValidator.isPasswordValid(decryptedPassword, decryptedConfirmPassword);

        // Generate unique userID
        String userID = generateUniqueUserId();

        // Create and store user credentials in database (CALOWIN_SECURE)
        UserEntity user = new UserEntity(userID, email, passwordEncoder.encode(decryptedPassword));
        calowinSecureDBRepository.save(user);

        // Create and store user profile in database (CALOWIN)
        ProfileEntity profile = new ProfileEntity(userID, name, weight, "");
        calowinDBRepository.save(profile);

        // Prepare and return user data to frontend
        return new LoginResponseDTO(user.getUserID(), user.getEmail(), profile.getName(), profile.getWeight(), profile.getBio());
    }

    // Login method
    public LoginResponseDTO login(String email, String encryptedPassword) throws Exception {
        String decryptedPassword = Decryptor.decrypt(encryptedPassword, SECRET_KEY);

        UserEntity user = calowinSecureDBRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(decryptedPassword, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        ProfileEntity profile = calowinDBRepository.findByUserID(user.getUserID())
                .orElseThrow(() -> new RuntimeException("Failed to retrieve user data"));

        return new LoginResponseDTO(user.getUserID(), user.getEmail(), profile.getName(), profile.getWeight(), profile.getBio());
    }

    // Delete account method
    @Transactional(transactionManager = "calowinSecureDBTransactionManager")
    public void deleteAccount(String userID, int OTP) throws Exception {
        // Logic for deleting the account can be implemented here once email service is set up
    }

    // Method to generate a unique 8-character userID
    private String generateUniqueUserId() {
        String userID;
        boolean exists;

        // Loop until a unique userID is generated
        do {
            userID = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
            exists = calowinSecureDBRepository.existsByUserID(userID);
        } while (exists);

        return userID;
    }

    
}
