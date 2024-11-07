package com.Database.CalowinDB;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Account.Entities.FriendRelationshipEntry;

@Repository
public interface FriendRelationshipRepository extends JpaRepository<FriendRelationshipEntry, String> {
    Optional<FriendRelationshipEntry> findByUserID(String userID);
    void deleteByUserID(String userID);
}