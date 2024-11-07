package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.dto.FriendRelationshipDTO;
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
    private UserInfoService userInfoService;

    // Method to convert FriendRelationship to FriendRelationshipDTO
    private FriendRelationshipDTO convertToDTO(FriendRelationship relationship) {
        String userName = userInfoService.getUserNameById(relationship.getUser().getUserId());
        String friendUserName = userInfoService.getUserNameById(relationship.getFriendUser().getUserId());

        return new FriendRelationshipDTO(
            relationship.getUser().getUserId(),
            userName,
            relationship.getFriendUser().getUserId(),
            friendUserName,
            relationship.getStatus()
        );
    }

    public FriendRelationshipDTO sendFriendRequest(String senderId, String receiverId) {
        try {
            Optional<FriendRelationship> existingRequest = repository.findByUniqueIdAndFriendUser_UserId(senderId, receiverId);
            if (existingRequest.isPresent()) {
                throw new IllegalArgumentException("Friend request already sent.");
            }

            UserInfo senderUser = userInfoService.getUserInfoById(senderId);
            UserInfo receiverUser = userInfoService.getUserInfoById(receiverId);
            if (senderUser == null || receiverUser == null) {
                throw new IllegalArgumentException("Sender or receiver user not found.");
            }

            FriendRelationship relationship = new FriendRelationship();
            relationship.setUser(senderUser);
            relationship.setFriendUser(receiverUser);
            relationship.setFriendedOn(LocalDateTime.now());
            relationship.setStatus("PENDING");

            return convertToDTO(repository.save(relationship));
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error occurred", e);
        }
    }

    public List<FriendRelationshipDTO> getPendingRequests(String userId) {
        return repository.findByFriendUser_UserIdAndStatus(userId, "PENDING").stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public FriendRelationshipDTO respondToRequest(String senderId, String receiverId, String status) {
        FriendRelationship relationship = repository.findByUniqueIdAndFriendUser_UserId(senderId, receiverId)
            .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (!"PENDING".equals(relationship.getStatus())) {
            throw new IllegalArgumentException("Request is not pending.");
        }

        if (!"ACCEPTED".equals(status) && !"REJECTED".equals(status)) {
            throw new IllegalArgumentException("Invalid status. Use 'ACCEPTED' or 'REJECTED'.");
        }

        relationship.setStatus(status);
        return convertToDTO(repository.save(relationship));
    }

    public List<FriendRelationshipDTO> getFriendList(String userId) {
        return repository.findAllFriendRelationships(userId, "ACCEPTED").stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public FriendRelationshipDTO acceptFriendRequest(String senderId, String receiverId) {
        FriendRelationship relationship = repository.findByUniqueIdAndFriendUser_UserId(senderId, receiverId)
            .orElseThrow(() -> new IllegalArgumentException("Friend request not found."));
        
        if (!"PENDING".equals(relationship.getStatus())) {
            throw new IllegalArgumentException("Request is not pending.");
        }
        
        relationship.setStatus("ACCEPTED");
        return convertToDTO(repository.save(relationship));
    }

    public FriendRelationshipDTO rejectFriendRequest(String senderId, String receiverId) {
        FriendRelationship relationship = repository.findByUniqueIdAndFriendUser_UserId(senderId, receiverId)
            .orElseThrow(() -> new IllegalArgumentException("Friend request not found."));
        
        if (!"PENDING".equals(relationship.getStatus())) {
            throw new IllegalArgumentException("Request is not pending.");
        }

        relationship.setStatus("REJECTED");
        return convertToDTO(repository.save(relationship));
    }

    public void cancelFriendRequest(String senderId, String receiverId) {
        FriendRelationship relationship = repository.findByUniqueIdAndFriendUser_UserId(senderId, receiverId)
            .orElseThrow(() -> new IllegalArgumentException("Friend request not found."));
        
        if (!"PENDING".equals(relationship.getStatus())) {
            throw new IllegalArgumentException("Cannot cancel a non-pending request.");
        }
        
        repository.delete(relationship);
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

        return "STRANGER";
    }

    public void removeFriend(String userId, String friendId) {
        Optional<FriendRelationship> relationship = repository.findByUniqueIdAndFriendUser_UserId(userId, friendId);
    
        // Check if the reverse relationship exists (friend is userId and user is friendId)
        if (relationship.isEmpty()) {
            relationship = repository.findByUniqueIdAndFriendUser_UserId(friendId, userId);
        }
    
        // If relationship exists and is accepted, delete it
        if (relationship.isPresent() && "ACCEPTED".equals(relationship.get().getStatus())) {
            repository.delete(relationship.get());
        } else {
            throw new IllegalArgumentException("No friendship found to remove.");
        }
    }
    
}
