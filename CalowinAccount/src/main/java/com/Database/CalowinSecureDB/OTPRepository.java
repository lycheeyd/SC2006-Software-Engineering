package com.Database.CalowinSecureDB;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Account.Entities.ActionType;
import com.Account.Entities.OTPEntry;

@Repository
public interface OTPRepository extends JpaRepository<OTPEntry, String> {
    Optional<OTPEntry> findByEmailAndActionType(String email, ActionType actionType);
    void deleteByEmailAndActionType(String email, ActionType actionType);
    
}