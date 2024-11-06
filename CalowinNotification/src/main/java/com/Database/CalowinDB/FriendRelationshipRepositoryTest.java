package main.java.com.Database.CalowinDB;

import com.Relationship.Entity.FriendRelationship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // This annotation will configure an in-memory database for testing
@ActiveProfiles("test") // Assuming a "test" profile is configured in application properties for testing
public class FriendRelationshipRepositoryTest {

    @Autowired
    private FriendRelationshipRepository friendRelationshipRepository;

    @BeforeEach
    void setUp() {
        // Setup initial data for testing
        FriendRelationship relationship1 = new FriendRelationship();
        relationship1.setFriendUniqueId("00000001");
        relationship1.setStatus("REQUESTSENT");
        friendRelationshipRepository.save(relationship1);

        FriendRelationship relationship2 = new FriendRelationship();
        relationship2.setFriendUniqueId("00000001");
        relationship2.setStatus("ACCEPTED");
        friendRelationshipRepository.save(relationship2);
    }

    @Test
    void testExecuteRawQuery() {
        String userId = "00000001";

        // Run the query to retrieve friend requests
        List<Map<String, Object>> friendRequests = friendRelationshipRepository.executeRawQuery(userId);

        // Assert that only the request with "REQUESTSENT" status is retrieved
        assertThat(friendRequests).hasSize(1);
        assertThat(friendRequests.get(0).get("status")).isEqualTo("REQUESTSENT");
        assertThat(friendRequests.get(0).get("Friend_Unique_ID")).isEqualTo(userId);
    }
}

