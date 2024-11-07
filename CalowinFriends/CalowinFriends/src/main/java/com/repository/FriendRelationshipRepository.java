package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.models.FriendRelationship;

public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship, String> {
    
    boolean existsByUniqueIdAndFriendUser_UserId(String uniqueId, String friendUserId);
    
    List<FriendRelationship> findByUniqueId(String uniqueId);
    
    // Use friendUser_UserId to access the ID of the friend through the friendUser association
    List<FriendRelationship> findByFriendUser_UserId(String friendUserId);
    
    List<FriendRelationship> findByFriendUser_UserIdAndStatus(String friendUserId, String status);
    
    Optional<FriendRelationship> findByUniqueIdAndFriendUser_UserId(String uniqueId, String friendUserId);
    
    @Query("SELECT f FROM FriendRelationship f WHERE (f.uniqueId = :userId OR f.friendUser.userId = :userId) AND f.status = :status")
    List<FriendRelationship> findByUserIdInEitherColumnAndStatus(@Param("userId") String userId, @Param("status") String status);
    
    @Query("SELECT f FROM FriendRelationship f WHERE (f.uniqueId = :userId OR f.friendUser.userId = :userId) AND f.status = :status")
    List<FriendRelationship> findAllFriendRelationships(@Param("userId") String userId, @Param("status") String status);
}
