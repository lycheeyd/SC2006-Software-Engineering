package com.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.DataTransferObject.FriendRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/friends")
public class NotificationController {

    @Autowired
    private FriendRelationshipService friendRelationshipService;

    @GetMapping("/requests/{userId}")
    public List<FriendRelationship> getFriendRequests(@PathVariable String userId) {
        return friendRelationshipService.getPendingFriendRequests(userId);
    }

    /*@PostMapping("/accept")
    public String acceptFriendRequest(@RequestParam String uniqueId, @RequestParam String friendUniqueId) {
        friendRelationshipService.acceptFriendRequest(uniqueId, friendUniqueId);
        return "Friend request accepted";
    }*/

    @GetMapping("/friend-requests/{userId}")
    public List<FriendRequestDTO> getIncomingFriendRequests(@PathVariable String userId) {
        return friendRelationshipService.getPendingFriendRequests(userId).stream()
                .map(request -> new FriendRequestDTO(
                        request.getFriendUniqueId(), // Assuming this is the sender's ID
                        "SenderNamePlaceholder"))
                .collect(Collectors.toList());
    }
}

