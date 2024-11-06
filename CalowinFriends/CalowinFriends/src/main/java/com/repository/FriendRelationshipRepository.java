package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.models.FriendRelationship;


public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship, String> {
    boolean existsByUniqueIdAndFriendUniqueId(String uniqueId, String friendUniqueId);
    List<FriendRelationship> findByUniqueId(String uniqueId);
    List<FriendRelationship> findByFriendUniqueId(String friendUniqueId);
    List<FriendRelationship> findByFriendUniqueIdAndStatus(String friendUniqueId, String status);
    Optional<FriendRelationship> findByUniqueIdAndFriendUniqueId(String uniqueId, String friendUniqueId);

    @Query("SELECT f FROM FriendRelationship f WHERE (f.uniqueId = :userId OR f.friendUniqueId = :userId) AND f.status = :status")
    List<FriendRelationship> findByUserIdInEitherColumnAndStatus(@Param("userId") String userId, @Param("status") String status);
    
}