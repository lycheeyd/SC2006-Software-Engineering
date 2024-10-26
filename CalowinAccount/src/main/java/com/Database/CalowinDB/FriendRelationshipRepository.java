package com.Database.CalowinDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Account.FriendRelationship;

import java.util.List;

@Repository
public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship, String> {

    // Custom SQL query to fetch pending friend requests for a specific user
    @Query(value = "SELECT * FROM FriendRelationship WHERE Friend_Unique_ID = :userId AND status = 'REQUESTSENT'", nativeQuery = true)
    List<FriendRelationship> findPendingFriendRequests(@Param("userId") String userId);
}


