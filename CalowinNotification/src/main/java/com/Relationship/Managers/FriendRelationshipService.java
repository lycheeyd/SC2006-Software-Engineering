package main.java.com.Relationship.Managers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Database.CalowinDB.FriendRelationshipRepository;

import java.util.List;
import java.util.Map;

@Service
public class FriendRelationshipService {

    @Autowired
    private FriendRelationshipRepository friendRelationshipRepository;

    public List<Map<String, Object>> getAllPendingRequestsForUser(String userId) {
        // Directly call the repository method with userId as the parameter
        return friendRelationshipRepository.executeRawQuery(userId);
    }
}

