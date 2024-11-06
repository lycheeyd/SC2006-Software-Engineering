package com.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.models.FriendRelationship;
import com.repository.FriendRelationshipRepository;

@Service
public class FriendRelationshipService {

    @Autowired
    private FriendRelationshipRepository repository;

    public FriendRelationship sendFriendRequest(String senderId, String receiverId) {
        try {
            // Check if the request already exists
            Optional<FriendRelationship> existingRequest = repository.findByUniqueIdAndFriendUniqueId(senderId, receiverId);
            if (existingRequest.isPresent()) {
                throw new IllegalArgumentException("Friend request already sent.");
            }
            
            // Create a new FriendRelationship object
            FriendRelationship relationship = new FriendRelationship();
            relationship.setUniqueId(senderId);
            relationship.setFriendUniqueId(receiverId);
            relationship.setFriendedOn(LocalDateTime.now());
            relationship.setStatus("PENDING");
            
            // Save the request to the database
            return repository.save(relationship);
        } catch (DataAccessException e) {
            // Handle database-related exceptions
            throw new RuntimeException("Database error occurred", e);
        } catch (IllegalArgumentException e) {
            // Handle illegal argument exception
            throw e; // Re-throw to propagate the specific error
        } catch (Exception e) {
            // Handle any other unforeseen exceptions
            throw new RuntimeException("Unexpected error occurred", e);
        }
    }
    
    public List<FriendRelationship> getFriendRequests(String userId) {
        return repository.findByFriendUniqueId(userId);
    }

    public FriendRelationship updateStatus(String uniqueId, String status) {
        FriendRelationship relationship = repository.findById(uniqueId).orElseThrow();
        relationship.setStatus(status);
        return repository.save(relationship);
    }
    public List<FriendRelationship> getPendingRequests(String receiverId) {
        return repository.findByFriendUniqueIdAndStatus(receiverId, "PENDING");
    }

    public FriendRelationship respondToRequest(String senderId, String receiverId, String status) {
        FriendRelationship relationship = repository.findByUniqueIdAndFriendUniqueId(senderId, receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        relationship.setStatus(status);
        return repository.save(relationship);
    }
}