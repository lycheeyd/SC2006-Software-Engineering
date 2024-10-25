package com.Account.SecurityUtilities;

public class PasswordValidator {
    // Utility function to validate the password
    public static void isPasswordValid(String password, String confirmPassword) throws Exception {
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
