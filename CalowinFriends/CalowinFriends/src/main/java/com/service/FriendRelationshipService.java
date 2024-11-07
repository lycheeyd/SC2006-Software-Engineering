package com.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ENUM.FriendStatus;
import com.dto.FriendRelationshipDTO;
import com.models.FriendRelationship;
import com.models.FriendRelationshipId;
import com.repository.FriendRelationshipRepository;

import jakarta.transaction.Transactional;

@Service
public class FriendRelationshipService {

    @Autowired
    private FriendRelationshipRepository repository;

    @Autowired
    private UserInfoService userInfoService;

    private FriendRelationshipDTO convertToDTO(FriendRelationship relationship) {
        String userName = userInfoService.getUserNameById(relationship.getId().getUniqueId());
        String friendUserName = userInfoService.getUserNameById(relationship.getId().getFriendUniqueId());

        return new FriendRelationshipDTO(
            relationship.getId().getUniqueId(),
            userName,
            relationship.getId().getFriendUniqueId(),
            friendUserName,
            relationship.getStatus()
        );
    }

    @Transactional
    public FriendRelationshipDTO sendFriendRequest(String senderId, String receiverId) {
        FriendRelationshipId id = new FriendRelationshipId(senderId, receiverId);
        FriendRelationshipId reverseId = new FriendRelationshipId(receiverId, senderId);

        // Check if a relationship already exists in either direction
        if (repository.existsById(id) || repository.existsById(reverseId)) {
            throw new IllegalArgumentException("Friend request already exists.");
        }

        // Create and save new friend relationship
        FriendRelationship relationship = new FriendRelationship();
        relationship.setId(id);
        relationship.setFriendedOn(LocalDateTime.now());
        relationship.setStatus("PENDING");

        return convertToDTO(repository.save(relationship));
    }

    public List<FriendRelationshipDTO> getPendingRequests(String userId) {
        return repository.findByIdFriendUniqueIdAndStatus(userId, "PENDING").stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public FriendRelationshipDTO respondToRequest(String senderId, String receiverId, String status) {
        FriendRelationshipId id = new FriendRelationshipId(senderId, receiverId);

        FriendRelationship relationship = repository.findById(id)
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
        return repository.findByIdUniqueIdOrIdFriendUniqueIdAndStatus(userId, userId, "ACCEPTED").stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public FriendRelationshipDTO acceptFriendRequest(String senderId, String receiverId) {
        return respondToRequest(senderId, receiverId, "ACCEPTED");
    }

    public FriendRelationshipDTO rejectFriendRequest(String senderId, String receiverId) {
        return respondToRequest(senderId, receiverId, "REJECTED");
    }

    public void cancelFriendRequest(String senderId, String receiverId) {
        FriendRelationshipId id = new FriendRelationshipId(senderId, receiverId);
        FriendRelationship relationship = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Friend request not found"));

        if (!"PENDING".equals(relationship.getStatus())) {
            throw new IllegalArgumentException("Cannot cancel a non-pending request.");
        }

        repository.delete(relationship);
    }
    public FriendStatus getRelationshipStatus(String userId1, String userId2) {
        // Check for a direct relationship from userId1 to userId2
        Optional<FriendRelationship> directRelationship = repository.findById(new FriendRelationshipId(userId1, userId2));
        if (directRelationship.isPresent()) {
            String status = directRelationship.get().getStatus();
            switch (status) {
                case "ACCEPTED":
                    return FriendStatus.FRIEND;
                case "PENDING":
                    return FriendStatus.REQUESTSENT;
                case "REJECTED":
                    return FriendStatus.STRANGER;
            }
        }
    
        // Check for a reverse relationship from userId2 to userId1
        Optional<FriendRelationship> reverseRelationship = repository.findById(new FriendRelationshipId(userId2, userId1));
        if (reverseRelationship.isPresent()) {
            String status = reverseRelationship.get().getStatus();
            if ("PENDING".equals(status)) {
                return FriendStatus.REQUESTRECIEVED;
            } else if ("REJECTED".equals(status)) {
                return FriendStatus.STRANGER;
            }
        }
    
        // If no relationship exists, default to STRANGER
        return FriendStatus.STRANGER;
    }
    
    

    public void removeFriend(String userId, String friendId) {
        FriendRelationshipId id = new FriendRelationshipId(userId, friendId);
        FriendRelationshipId reverseId = new FriendRelationshipId(friendId, userId);

        Optional<FriendRelationship> relationship = repository.findById(id);

        if (relationship.isEmpty()) {
            relationship = repository.findById(reverseId);
        }

        if (relationship.isPresent() && "ACCEPTED".equals(relationship.get().getStatus())) {
            repository.delete(relationship.get());
        } else {
            throw new IllegalArgumentException("No friendship found to remove.");
        }
    }
}
