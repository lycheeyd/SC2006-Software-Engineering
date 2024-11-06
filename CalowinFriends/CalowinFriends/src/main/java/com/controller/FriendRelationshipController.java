package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.models.FriendRelationship;
import com.models.UserInfo;
import com.service.FriendRelationshipService;
import com.service.UserInfoService;

@RestController
@RequestMapping("/friend-requests")
public class FriendRelationshipController {

    @Autowired
    private FriendRelationshipService friendService;
    
    @Autowired
    private UserInfoService userInfoService; // For searching users by username or name

    // Send Friend Request
    @PostMapping("/send")
    public ResponseEntity<FriendRelationship> sendRequest(@RequestParam String senderId, @RequestParam String receiverId) {
        try {
            FriendRelationship result = friendService.sendFriendRequest(senderId, receiverId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // View Pending Friend Requests
    @GetMapping("/pending/{receiverId}")
    public ResponseEntity<List<FriendRelationship>> getPendingRequests(@PathVariable String receiverId) {
        try {
            List<FriendRelationship> result = friendService.getPendingRequests(receiverId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Respond to Friend Request (Accept or Reject)
    @PostMapping("/respond")
    public ResponseEntity<FriendRelationship> respondToRequest(
            @RequestParam String senderId, 
            @RequestParam String receiverId, 
            @RequestParam String status) {
        try {
            FriendRelationship result = friendService.respondToRequest(senderId, receiverId, status);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Cancel Friend Request
    @PostMapping("/cancel")
    public ResponseEntity<Void> cancelFriendRequest(@RequestParam String senderId, @RequestParam String receiverId) {
        try {
            friendService.cancelFriendRequest(senderId, receiverId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Accept Friend Request
    @PostMapping("/accept")
    public ResponseEntity<FriendRelationship> acceptFriendRequest(@RequestParam String senderId, @RequestParam String receiverId) {
        try {
            FriendRelationship result = friendService.acceptFriendRequest(senderId, receiverId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Reject Friend Request
    @PostMapping("/reject")
    public ResponseEntity<FriendRelationship> rejectFriendRequest(@RequestParam String senderId, @RequestParam String receiverId) {
        try {
            FriendRelationship result = friendService.rejectFriendRequest(senderId, receiverId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Remove Friend
    @PostMapping("/remove")
    public ResponseEntity<Void> removeFriend(@RequestParam String userId, @RequestParam String friendId) {
        try {
            friendService.removeFriend(userId, friendId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // View Friend List
    @GetMapping("/friends/{userId}")
    public ResponseEntity<List<FriendRelationship>> getFriendList(@PathVariable String userId) {
        try {
            List<FriendRelationship> result = friendService.getFriendList(userId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Search for Friends (by username or name)
    @GetMapping("/search")
    public ResponseEntity<List<UserInfo>> searchFriends(@RequestParam String searchTerm) {
        try {
            List<UserInfo> result = userInfoService.searchByUserIdOrName(searchTerm);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
