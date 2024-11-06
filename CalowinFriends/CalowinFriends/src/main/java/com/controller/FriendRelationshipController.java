package com.controller;

import com.ENUM.FriendRequestStatus;
import com.models.FriendRelationship;
import com.service.FriendRelationshipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend-requests")
public class FriendRelationshipController {

    @Autowired
    private FriendRelationshipService service;

    @PostMapping("/send")
    public FriendRelationship sendRequest(@RequestParam String senderId, @RequestParam String receiverId) {
        return service.sendFriendRequest(senderId, receiverId);
    }

    @GetMapping("/pending/{receiverId}")
    public List<FriendRelationship> getPendingRequests(@PathVariable String receiverId) {
        return service.getPendingRequests(receiverId);
    }

    @PostMapping("/respond")
    public FriendRelationship respondToRequest(@RequestParam String senderId, @RequestParam String receiverId, @RequestParam FriendRequestStatus status) {
        return service.respondToRequest(senderId, receiverId, status);
    }
}