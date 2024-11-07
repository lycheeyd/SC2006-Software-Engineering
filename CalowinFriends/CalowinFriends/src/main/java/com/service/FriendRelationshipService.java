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
import com.models.UserInfo;
import com.repository.FriendRelationshipRepository;

@Service
public class FriendRelationshipService {

    @Autowired
    private FriendRelationshipRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private UserInfoService userInfoService; // Ensure this service is available to fetch UserInfo

    public FriendRelationship sendFriendRequest(String senderId, String receiverId) {
        try {
            // Check if the request already exists
            Optional<FriendRelationship> existingRequest = repository.findByUniqueIdAndFriendUser_UserId(senderId, receiverId);
            if (existingRequest.isPresent()) {
                throw new IllegalArgumentException("Friend request already sent.");
            }
            
            // Fetch UserInfo objects for sender and receiver
            UserInfo senderUser = userInfoService.getUserInfoById(senderId);
            UserInfo receiverUser = userInfoService.getUserInfoById(receiverId);
            
            if (senderUser == null || receiverUser == null) {
                throw new IllegalArgumentException("Sender or receiver user not found.");
            }

            // Create a new FriendRelationship object
            FriendRelationship relationship = new FriendRelationship();
            relationship.setUser(senderUser);  // Set sender's UserInfo
            relationship.setFriendUser(receiverUser);  // Set receiver's UserInfo
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
        return repository.findByFriendUser_UserId(userId);
    }

    public FriendRelationship updateStatus(String uniqueId, String status) {
        FriendRelationship relationship = repository.findById(uniqueId).orElseThrow();
        relationship.setStatus(status);
        return repository.save(relationship);
    }

    public List<FriendRelationship> getPendingRequests(String receiverId) {
        return repository.findByFriendUser_UserIdAndStatus(receiverId, "PENDING");
    }

    public void cancelFriendRequest(String senderId, String receiverId) {
        FriendRelationship relationship = repository.findByUniqueIdAndFriendUser_UserId(senderId, receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found."));
        
        if (!"PENDING".equals(relationship.getStatus())) {
            throw new IllegalArgumentException("Cannot cancel a non-pending request.");
        }
        
        repository.delete(relationship);
    }

    public FriendRelationship acceptFriendRequest(String senderId, String receiverId) {
        FriendRelationship relationship = repository.findByUniqueIdAndFriendUser_UserId(senderId, receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found."));
        
        if (!"PENDING".equals(relationship.getStatus())) {
            throw new IllegalArgumentException("Request is not pending.");
        }
        
        relationship.setStatus("ACCEPTED");
        return repository.save(relationship);
    }

    public FriendRelationship rejectFriendRequest(String senderId, String receiverId) {
        FriendRelationship relationship = repository.findByUniqueIdAndFriendUser_UserId(senderId, receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found."));
        
        if (!"PENDING".equals(relationship.getStatus())) {
            throw new IllegalArgumentException("Request is not pending.");
        }

        relationship.setStatus("REJECTED");
        return repository.save(relationship);
    }

    public void removeFriend(String userId, String friendId) {
        Optional<FriendRelationship> relationship = repository.findByUniqueIdAndFriendUser_UserId(userId, friendId);
        
        if (relationship.isEmpty()) {
            relationship = repository.findByUniqueIdAndFriendUser_UserId(friendId, userId);
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
        FriendRelationship relationship = repository.findByUniqueIdAndFriendUser_UserId(senderId, receiverId)
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
                    UserInfo user = new UserInfo();
                    user.setUserId(rs.getString("Unique_ID"));
                    relationship.setUser(user);
                    
                    UserInfo friendUser = new UserInfo();
                    friendUser.setUserId(rs.getString("Friend_Unique_ID"));
                    relationship.setFriendUser(friendUser);
                    
                    relationship.setStatus(rs.getString("status"));
                    return relationship;
                }
            }
        );

        for (FriendRelationship relationship : relationships) {
            String uniqueId = relationship.getUser().getUserId();
            String friendUniqueId = relationship.getFriendUser().getUserId();
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

        return "STRANGER";
    }
}
