package com.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Database.CalowinDB.FriendRelationshipRepository;

import java.util.List;

@Service
public class FriendRelationshipService {

    @Autowired
    private FriendRelationshipRepository friendRelationshipRepository;

    public List<FriendRelationship> getPendingFriendRequests(String userId) {
        return friendRelationshipRepository.findByFriendUniqueIdAndStatus(userId, "REQUESTSENT");
    }
    
}

