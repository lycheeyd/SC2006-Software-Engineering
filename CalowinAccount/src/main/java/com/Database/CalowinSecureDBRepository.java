package com.Database;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Account.UserEntity;

@Primary
public interface CalowinSecureDBRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUserID(String userID);
    boolean existsByUserID(String userID);
}