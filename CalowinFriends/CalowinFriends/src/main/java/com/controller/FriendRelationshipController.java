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

import com.ENUM.FriendRequestStatus;
import com.models.FriendRelationship;
import com.service.FriendRelationshipService;

@RestController
@RequestMapping("/friend-requests")
public class FriendRelationshipController {

    @Autowired
    private FriendRelationshipService service;

    @PostMapping("/send")
    public ResponseEntity<FriendRelationship> sendRequest(@RequestParam String senderId, @RequestParam String receiverId) {
        try {
            FriendRelationship result = service.sendFriendRequest(senderId, receiverId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/pending/{receiverId}")
    public ResponseEntity<List<FriendRelationship>> getPendingRequests(@PathVariable String receiverId) {
        try {
            List<FriendRelationship> result = service.getPendingRequests(receiverId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/respond")
    public ResponseEntity<FriendRelationship> respondToRequest(@RequestParam String senderId, @RequestParam String receiverId, @RequestParam FriendRequestStatus status) {
        try {
            FriendRelationship result = service.respondToRequest(senderId, receiverId, status);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}