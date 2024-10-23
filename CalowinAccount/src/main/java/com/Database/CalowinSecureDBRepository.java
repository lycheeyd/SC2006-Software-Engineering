package com.Database;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Account.User;

public interface CalowinSecureDBRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserID(String userID);
    boolean existsByUserID(String userID);
}