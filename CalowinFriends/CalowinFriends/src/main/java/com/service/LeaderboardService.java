package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.dto.AchievementDTO;
import com.models.Achievement;
import com.models.FriendRelationship;
import com.models.UserInfo;

@Service
public class LeaderboardService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserInfoService userInfoService; // Service to fetch user names

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

    public List<String> getFriendsIds(String userId) {
        String sql = "SELECT * FROM FriendRelationship WHERE (Unique_ID = ? OR Friend_Unique_ID = ?) AND status = 'ACCEPTED'";
        
        List<FriendRelationship> relationships = jdbcTemplate.query(sql, friendRelationshipRowMapper, userId, userId);

        List<String> friendsIds = relationships.stream()
                .map(r -> r.getUniqueId().equals(userId) ? r.getFriendUser().getUserId() : r.getUniqueId())
                .distinct()
                .collect(Collectors.toList());

        friendsIds.add(userId); // Optionally include the user itself
        return friendsIds;
    }

    public List<AchievementDTO> getCarbonLeaderboard(String userId) {
        List<String> friendsIds = getFriendsIds(userId);

        String sql = "SELECT * FROM Achievement WHERE user_id IN (" +
                     friendsIds.stream().map(id -> "?").collect(Collectors.joining(", ")) + ")";

        List<Achievement> achievements = jdbcTemplate.query(
            sql,
            friendsIds.toArray(),
            achievementRowMapper
        );

        return achievements.stream()
            .sorted((a, b) -> Integer.compare(b.getTotalCarbonSaved(), a.getTotalCarbonSaved()))
            .map(a -> {
                AchievementDTO dto = new AchievementDTO();
                dto.setUserId(a.getUserId());
                dto.setUserName(getUserNameById(a.getUserId()));
                dto.setTotalCarbonSaved(a.getTotalCarbonSaved());
                dto.setTotalCalorieBurnt(a.getTotalCalorieBurnt());
                dto.setCarbonMedal(a.getCarbonMedal());
                dto.setCalorieMedal(a.getCalorieMedal());
                return dto;
            })
            .collect(Collectors.toList());
    }

    public List<AchievementDTO> getCaloriesLeaderboard(String userId) {
        List<String> friendsIds = getFriendsIds(userId);

        String sql = "SELECT * FROM Achievement WHERE user_id IN (" +
                     friendsIds.stream().map(id -> "?").collect(Collectors.joining(", ")) + ")";

        List<Achievement> achievements = jdbcTemplate.query(
            sql,
            friendsIds.toArray(),
            achievementRowMapper
        );

        return achievements.stream()
            .sorted((a, b) -> Integer.compare(b.getTotalCalorieBurnt(), a.getTotalCalorieBurnt()))
            .map(a -> {
                AchievementDTO dto = new AchievementDTO();
                dto.setUserId(a.getUserId());
                dto.setUserName(getUserNameById(a.getUserId()));
                dto.setTotalCarbonSaved(a.getTotalCarbonSaved());
                dto.setTotalCalorieBurnt(a.getTotalCalorieBurnt());
                dto.setCarbonMedal(a.getCarbonMedal());
                dto.setCalorieMedal(a.getCalorieMedal());
                return dto;
            })
            .collect(Collectors.toList());
    }

    public String getUserNameById(String userId) {
        String sql = "SELECT name FROM UserInfo WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, String.class);
    }
    
    private RowMapper<FriendRelationship> friendRelationshipRowMapper = new RowMapper<FriendRelationship>() {
        @Override
        public FriendRelationship mapRow(ResultSet rs, int rowNum) throws SQLException {
            FriendRelationship relationship = new FriendRelationship();
            relationship.setUniqueId(rs.getString("Unique_ID"));
    
            // Assuming that friendUser is already initialized, set its unique ID
            UserInfo friendUser = new UserInfo(); // Ensure friendUser is not null
            friendUser.setUserId(rs.getString("Friend_Unique_ID"));
            relationship.setFriendUser(friendUser);
    
            relationship.setStatus(rs.getString("status"));
            return relationship;
        }
    };
}
