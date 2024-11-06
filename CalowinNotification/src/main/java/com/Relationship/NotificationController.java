package main.java.com.Relationship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import main.java.com.Relationship.Managers.FriendRelationshipService;

//import com.DataTransferObject.FriendRequestDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private FriendRelationshipService friendRelationshipService;

    @GetMapping("/friend-requests/{userId}")
    public List<Map<String, Object>> getIncomingFriendRequests(@PathVariable String userId) {
        // Directly return the list of pending friend requests from the service
        return friendRelationshipService.getFriendRequestsForUser(userId);
    }
}

