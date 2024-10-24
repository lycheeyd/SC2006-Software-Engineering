package com.Database.CalowinDB;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Account.ProfileEntity;

@Repository
public interface CalowinDBRepository extends JpaRepository<ProfileEntity, String> {
    Optional<ProfileEntity> findByUserID(String userID);
    boolean existsByUserID(String userID);
}