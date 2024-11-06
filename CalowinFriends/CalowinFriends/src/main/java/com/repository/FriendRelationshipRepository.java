package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ENUM.FriendRequestStatus;
import com.models.FriendRelationship;
import java.util.List;
import java.util.Optional;


public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship, String> {
    boolean existsByUniqueIdAndFriendUniqueId(String uniqueId, String friendUniqueId);
    List<FriendRelationship> findByUniqueId(String uniqueId);
    List<FriendRelationship> findByFriendUniqueId(String friendUniqueId);
    List<FriendRelationship> findByFriendUniqueIdAndStatus(String friendUniqueId, FriendRequestStatus status);
    Optional<FriendRelationship> findByUniqueIdAndFriendUniqueId(String uniqueId, String friendUniqueId);
}