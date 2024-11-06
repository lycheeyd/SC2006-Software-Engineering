package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.models.Achievement;
import com.models.FriendRelationship;

@Service
public class LeaderboardService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper for Achievement to map SQL result set to Achievement objects
    private RowMapper<Achievement> achievementRowMapper = new RowMapper<Achievement>() {
        @Override
        public Achievement mapRow(ResultSet rs, int rowNum) throws SQLException {
            Achievement achievement = new Achievement();
            achievement.setUserId(rs.getString("user_id"));
            achievement.setTotalCarbonSaved(rs.getInt("total_carbon_saved"));
            achievement.setTotalCalorieBurnt(rs.getInt("total_calorie_burnt"));
            achievement.setCarbonMedal(rs.getString("carbon_medal"));
            achievement.setCalorieMedal(rs.getString("calorie_medal"));
            return achievement;
        }
    };

    // RowMapper for FriendRelationship to map SQL result set to FriendRelationship objects
    private RowMapper<FriendRelationship> friendRelationshipRowMapper = new RowMapper<FriendRelationship>() {
        @Override
        public FriendRelationship mapRow(ResultSet rs, int rowNum) throws SQLException {
            FriendRelationship relationship = new FriendRelationship();
            relationship.setUniqueId(rs.getString("Unique_ID"));
            relationship.setFriendUniqueId(rs.getString("Friend_Unique_ID"));
            relationship.setStatus(rs.getString("status"));
            return relationship;
        }
    };

    public List<String> getFriendsIds(String userId) {
        String sql = "SELECT * FROM FriendRelationship WHERE (Unique_ID = ? OR Friend_Unique_ID = ?) AND status = 'ACCEPTED'";
        
        // Fetch relationships from the database
        List<FriendRelationship> relationships = jdbcTemplate.query(sql, friendRelationshipRowMapper, userId, userId);
        
        // Log the relationships fetched from the repository
        // System.out.println("Fetched relationships for user " + userId + ":");
        relationships.forEach(r -> System.out.println("Unique_ID: " + r.getUniqueId() + ", Friend_Unique_ID: " + r.getFriendUniqueId() + ", Status: " + r.getStatus()));
    
        // Collect friend IDs based on which side the user appears in the relationship
        List<String> friendsIds = relationships.stream()
                .map(r -> {
                    if (r.getUniqueId().equals(userId)) {
                        return r.getFriendUniqueId();  // If userId matches Unique_ID, take Friend_Unique_ID
                    } else {
                        return r.getUniqueId();  // Otherwise, take Unique_ID as the friend ID
                    }
                })
                .distinct() // Ensure unique friend IDs
                .collect(Collectors.toList());
                
        friendsIds.add(userId);

        // System.out.println("Friends' IDs for user " + userId + ": " + friendsIds);
        return friendsIds;
    }
    public List<Achievement> getCarbonLeaderboard(String userId) {
        List<String> friendsIds = getFriendsIds(userId);
        System.out.println("Friends' IDs for user " + userId + ": " + friendsIds);
    
        return jdbcTemplate.query(
            "SELECT * FROM Achievement WHERE user_id IN (" +
            friendsIds.stream().map(id -> "?").collect(Collectors.joining(", ")) + ")",
            friendsIds.toArray(),
            achievementRowMapper
        ).stream()
         .sorted((a, b) -> Integer.compare(b.getTotalCarbonSaved(), a.getTotalCarbonSaved()))
         .peek(a -> System.out.println(a.getUserId() + ": " + a.getTotalCarbonSaved() + ", Carbon Medal: " + a.getCarbonMedal() + ", Calorie Medal: " + a.getCalorieMedal()))
         .collect(Collectors.toList());
    }
    
    public List<Achievement> getCaloriesLeaderboard(String userId) {
        List<String> friendsIds = getFriendsIds(userId);
        System.out.println("Friends' IDs for user " + userId + ": " + friendsIds);
    
        return jdbcTemplate.query(
            "SELECT * FROM Achievement WHERE user_id IN (" +
            friendsIds.stream().map(id -> "?").collect(Collectors.joining(", ")) + ")",
            friendsIds.toArray(),
            achievementRowMapper
        ).stream()
         .sorted((a, b) -> Integer.compare(b.getTotalCalorieBurnt(), a.getTotalCalorieBurnt()))
         .peek(a -> System.out.println(a.getUserId() + ": " + a.getTotalCalorieBurnt() + ", Carbon Medal: " + a.getCarbonMedal() + ", Calorie Medal: " + a.getCalorieMedal()))
         .collect(Collectors.toList());
    }
    
}
