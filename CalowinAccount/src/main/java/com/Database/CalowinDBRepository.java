package com.Database;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Account.Profile;

public interface CalowinDBRepository extends JpaRepository<Profile, String> {
    Optional<Profile> findByUserID(String userID);
    boolean existsByUserID(String userID);
}