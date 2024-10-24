package com.Database.CalowinSecureDB;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Account.OTPEntity;

@Repository
public interface OTPRepository extends JpaRepository<OTPEntity, String> {
    Optional<OTPEntity> findByEmail(String email);
    void deleteByEmail(String email);  // Delete existing OTP for a user
}