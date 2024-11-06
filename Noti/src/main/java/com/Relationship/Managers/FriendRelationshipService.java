package main.java.com.Relationship.Managers;

import org.springframework.stereotype.Service;
import com.Database.CalowinDB.FriendRelationshipRepository;

import java.util.List;
import java.util.Map;

@Service
public class FriendRelationshipService {

    private final FriendRelationshipRepository friendRelationshipRepository;

    public FriendRelationshipService(FriendRelationshipRepository friendRelationshipRepository) {
        this.friendRelationshipRepository = friendRelationshipRepository;
    }

    public List<Map<String, Object>> getFriendRequestsForUser(String userId) {
        // Call the repository to execute the query based on userId
        return friendRelationshipRepository.executeRawQuery(userId);
    }
}
