package com.Database.CalowinDB;

import com.Account.FriendRelationship;
import com.Account.UserStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship, Long> {
    List<FriendRelationship> findByUserIdAndStatus(Long userId, UserStatusEnum status);
    Optional<FriendRelationship> findByUserIdAndFriendId(Long userId, Long friendId);
    List<FriendRelationship> findByFriendUniqueIdAndStatus(String friendUniqueId, String status);
    
}

