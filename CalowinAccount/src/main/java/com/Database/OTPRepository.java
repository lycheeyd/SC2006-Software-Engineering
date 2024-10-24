package com.Database;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Account.OTPEntity;

public interface OTPRepository extends JpaRepository<OTPEntity, String> {
    Optional<OTPEntity> findByEmail(String email);
    void deleteByEmail(String email);  // Delete existing OTP for a user
}