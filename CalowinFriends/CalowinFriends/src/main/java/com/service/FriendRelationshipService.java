package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.models.FriendRelationship;
import com.repository.FriendRelationshipRepository;

@Service
public class FriendRelationshipService {

    @Autowired
    private FriendRelationshipRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
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


    public void cancelFriendRequest(String senderId, String receiverId) {
        FriendRelationship relationship = repository.findByUniqueIdAndFriendUniqueId(senderId, receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found."));
        
        if (!"PENDING".equals(relationship.getStatus())) {
            throw new IllegalArgumentException("Cannot cancel a non-pending request.");
        }
        
        repository.delete(relationship);
    }

    public FriendRelationship acceptFriendRequest(String senderId, String receiverId) {
        FriendRelationship relationship = repository.findByUniqueIdAndFriendUniqueId(senderId, receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found."));
        
        if (!"PENDING".equals(relationship.getStatus())) {
            throw new IllegalArgumentException("Request is not pending.");
        }
        
        relationship.setStatus("ACCEPTED");
        return repository.save(relationship);
    }

    public FriendRelationship rejectFriendRequest(String senderId, String receiverId) {
        FriendRelationship relationship = repository.findByUniqueIdAndFriendUniqueId(senderId, receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found."));
        
        if (!"PENDING".equals(relationship.getStatus())) {
            throw new IllegalArgumentException("Request is not pending.");
        }

        relationship.setStatus("REJECTED");
        return repository.save(relationship);
    }

    public void removeFriend(String userId, String friendId) {
        Optional<FriendRelationship> relationship = repository.findByUniqueIdAndFriendUniqueId(userId, friendId);
        
        if (relationship.isEmpty()) {
            relationship = repository.findByUniqueIdAndFriendUniqueId(friendId, userId);
        }

        if (relationship.isPresent() && "ACCEPTED".equals(relationship.get().getStatus())) {
            repository.delete(relationship.get());
        } else {
            throw new IllegalArgumentException("No friendship found to remove.");
        }
    }
    public List<FriendRelationship> getFriendList(String userId) {
        return repository.findAllFriendRelationships(userId, "ACCEPTED");
    }

    public FriendRelationship respondToRequest(String senderId, String receiverId, String status) {
        FriendRelationship relationship = repository.findByUniqueIdAndFriendUniqueId(senderId, receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (!"PENDING".equals(relationship.getStatus())) {
            throw new IllegalArgumentException("Request is not pending.");
        }

        if (!"ACCEPTED".equals(status) && !"REJECTED".equals(status)) {
            throw new IllegalArgumentException("Invalid status. Use 'ACCEPTED' or 'REJECTED'.");
        }

        relationship.setStatus(status);
        return repository.save(relationship);
    }
    
    public String getRelationshipStatus(String userId1, String userId2) {
        // SQL to check for any relationship where either user references the other
        String sql = "SELECT * FROM FriendRelationship WHERE " +
                     "(Unique_ID = ? AND Friend_Unique_ID = ?) OR " +
                     "(Unique_ID = ? AND Friend_Unique_ID = ?)";

        List<FriendRelationship> relationships = jdbcTemplate.query(
            sql, 
            new Object[]{userId1, userId2, userId2, userId1}, 
            new RowMapper<FriendRelationship>() {
                @Override
                public FriendRelationship mapRow(ResultSet rs, int rowNum) throws SQLException {
                    FriendRelationship relationship = new FriendRelationship();
                    relationship.setUniqueId(rs.getString("Unique_ID"));
                    relationship.setFriendUniqueId(rs.getString("Friend_Unique_ID"));
                    relationship.setStatus(rs.getString("status"));
                    return relationship;
                }
            }
        );

        // Check the relationships based on the rules provided
        for (FriendRelationship relationship : relationships) {
            String uniqueId = relationship.getUniqueId();
            String friendUniqueId = relationship.getFriendUniqueId();
            String status = relationship.getStatus();

            if ((uniqueId.equals(userId1) && friendUniqueId.equals(userId2)) ||
                (uniqueId.equals(userId2) && friendUniqueId.equals(userId1))) {

                if ("ACCEPTED".equals(status)) {
                    return "FRIEND";
                } else if ("PENDING".equals(status)) {
                    if (uniqueId.equals(userId1) && friendUniqueId.equals(userId2)) {
                        return "REQUESTSENT";
                    } else if (uniqueId.equals(userId2) && friendUniqueId.equals(userId1)) {
                        return "REQUESTRECEIVED";
                    }
                } else if ("REJECTED".equals(status)) {
                    return "STRANGER";
                }
            }
        }

        // If no relationship is found, return STRANGER
        return "STRANGER";
    }

}