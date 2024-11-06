package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class RequestController {

    private final RestTemplate restTemplate;

    @Autowired
    public RequestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{userId}/friend-requests")
    public ResponseEntity<?> getFriendRequests(@PathVariable("userId") String userId) {
        try {
            // Communicate with NotificationController in CalowinNotification via HTTP
            String url = "http://localhost:8084/notifications/friend-requests/" + userId;
            List<Map<String, Object>> friendRequests = restTemplate.getForObject(url, List.class);

            if (friendRequests == null || friendRequests.isEmpty()) {
                return new ResponseEntity<>("No friend requests found for the given userId.", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(friendRequests, HttpStatus.OK);
        } catch (Exception e) {
            // Handle and return error response
            return new ResponseEntity<>("Failed to retrieve friend requests: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
