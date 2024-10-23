package com.Account;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.DataTransferObject.ResponseDTO;
import com.Database.CalowinDBRepository;
import com.Database.CalowinSecureDBRepository;

import org.apache.commons.lang3.RandomStringUtils;


@Service
public class UserService {

    @Autowired
    @Qualifier("CalowinSecureDBTransactionManager")
    private PlatformTransactionManager CalowinSecureDBTransactionManager;

    @Autowired
    @Qualifier("CalowinDBTransactionManager")
    private PlatformTransactionManager CalowinDBTransactionManager;

    @Autowired
    private CalowinSecureDBRepository calowinSecureDBRepository;

    @Autowired
    private CalowinDBRepository calowinDBRepository; 

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String SECRET_KEY = "ASK RAPHEL FOR KEY"; // Should be a 16/32-byte key

    // Signup method
    public ResponseDTO signup(String email, String encryptedPassword, String encryptedConfirmPassword, String name, float weight) throws Exception {
        // Check if user exist
        if (calowinSecureDBRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        String decryptedPassword = Decryptor.decrypt(encryptedPassword, SECRET_KEY);
        String decryptedConfirmPassword = Decryptor.decrypt(encryptedConfirmPassword, SECRET_KEY);
        
        // Check if password match
        if (!decryptedPassword.equals(decryptedConfirmPassword)) {
            throw new RuntimeException("Password does not match");
        }

        // Check if password meet requirementz
        if (!isPasswordValid(decryptedPassword)) {
            throw new RuntimeException("Invalid password");
        } 

        String userID = generateUniqueUserId();

        User user = new User(userID, email, passwordEncoder.encode(decryptedPassword));
        calowinSecureDBRepository.save(user);

        Profile profile = new Profile(userID, name, weight);
        calowinDBRepository.save(profile);

        return new ResponseDTO(userID, email, name, weight, "");

    }

    // Login method
    public ResponseDTO login(String email, String encryptedPassword) throws Exception {
        String decryptedPassword = Decryptor.decrypt(encryptedPassword, SECRET_KEY);

        User user = calowinSecureDBRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(decryptedPassword, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        Profile profile = calowinDBRepository.findByUserID(user.getUserID())
        .orElseThrow(() -> new RuntimeException("Failed to retrieve userdata"));;

        return new ResponseDTO(user.getUserID(), user.getEmail(), profile.getName(), profile.getWeight(), profile.getBio());
    
    }

    // Change password method
    public Map<String, Boolean> changePassword(String userid, String oldPassword, String newPassword, String confirm_newPassword) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("userExists", false);
        response.put("passwordsMatch", false);
        response.put("validPassword", false);
        response.put("correctOldPassword", false);
        response.put("passwordChanged", false);

        try {
            // Check if user exist
            User user = calowinSecureDBRepository.findByUserID(userid)
                    .orElseThrow(() -> new RuntimeException("Invalid user"));
            response.put("userExists", true);
    
            // Check if password match
            if (!newPassword.equals(confirm_newPassword)) {
                return response;
            } else {
                response.put("passwordsMatch", true);
            }
    
            // Check if password meet requirement
            if (!isPasswordValid(newPassword)) {
                return response;
            } else {
                response.put("validPassword", true);
            }
    
            // Authenticate old password
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                return response;
            } else {
                response.put("correctOldPassword", true);
            }
    
            user.setPassword(newPassword);
            calowinSecureDBRepository.save(user);
            response.put("passwordChanged", true);
    
        } catch (RuntimeException e) {
            return response;
        }
    
        return response;
    }
    
    // Utility function to validate the password
    private boolean isPasswordValid(String password) {
        // At least 8 characters, 1 uppercase, and 1 special character
        String passwordPattern = "^(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=.{8,}).*$";
        return password.matches(passwordPattern);
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
