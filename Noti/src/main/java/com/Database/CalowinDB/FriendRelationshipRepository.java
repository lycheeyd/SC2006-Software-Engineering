package com.Database.CalowinDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Relationship.Entity.FriendRelationship;

import java.util.List;
import java.util.Map;

@Repository
public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship,String>{

    @Query(value = "SELECT * FROM FriendRelationship WHERE Friend_Unique_ID = :userId AND status = 'REQUESTSENT'", nativeQuery = true)
    List<Map<String, Object>> executeRawQuery(@Param("userId") String userId);

    // List<FriendRelationship> findPendingFriendRequests(@Param("userId") String userId);

    //List<FriendRelationship> findAllRecords();
}

