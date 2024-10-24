package com.Database.CalowinSecureDB;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Account.UserEntity;

@Repository
@Primary
public interface CalowinSecureDBRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUserID(String userID);
    boolean existsByUserID(String userID);
}