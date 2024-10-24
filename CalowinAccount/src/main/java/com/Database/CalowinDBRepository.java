package com.Database;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Account.ProfileEntity;

public interface CalowinDBRepository extends JpaRepository<ProfileEntity, String> {
    Optional<ProfileEntity> findByUserID(String userID);
    boolean existsByUserID(String userID);
}