package com.example.CalowinTrip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/achievements")
public class AchievementController {

    private final Achievement achievement;

    public AchievementController() {
        this.achievement = new Achievement();
    }

   // Endpoint to add trip metrics (carbon saved and calories burnt)
@PostMapping("/addTripMetrics")
public void addTripMetrics(@RequestParam int carbonSaved, @RequestParam int caloriesBurnt, @RequestBody Trip trip) {
    // Fetch the user's achievement
    Achievement userAchievement = getUserAchievement(trip.getUserId());
    
    // Add the trip metrics to the user's achievement
    userAchievement.addTripExperience(carbonSaved, caloriesBurnt);
    
    // Save or update the user's achievement in the database
    try {
        saveOrUpdateAchievement(userAchievement, trip); // Save using user-specific data
    } catch (SQLException e) {
        e.printStackTrace(); // Handle exception
    }
}

// Method to retrieve or create an achievement for the user
private Achievement getUserAchievement(String userId) {
    // You can fetch the user's achievement from the database here (e.g., using a SELECT query)
    // If the user doesn't have an achievement, create a new one
    Achievement achievement = new Achievement(); // Create a new one if not found
    try (Connection conn = DatabaseConnection.getConnection()) {
        String query = "SELECT * FROM achievement WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // If the user exists, populate the Achievement object with their data
                achievement.setTotalCarbonSavedExp(rs.getInt("total_carbon_saved"));
                achievement.setTotalCalorieBurntExp(rs.getInt("total_calorie_burnt"));
                achievement.setCarbonSavedMedal(rs.getString("carbon_medal"));
                achievement.setCalorieBurntMedal(rs.getString("calorie_medal"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Handle exception
    }
    return achievement;
}

// Save or update achievement in the database
private void saveOrUpdateAchievement(Achievement achievement, Trip trip) throws SQLException {
    String updateQuery = "UPDATE achievement SET total_carbon_saved = ?, total_calorie_burnt = ?, carbon_medal = ?, calorie_medal = ? WHERE user_id = ?";
    String insertQuery = "INSERT INTO achievement (user_id, total_carbon_saved, total_calorie_burnt, carbon_medal, calorie_medal) VALUES (?, ?, ?, ?, ?)";
    
    try (Connection conn = DatabaseConnection.getConnection()) {
        String query = "SELECT * FROM achievement WHERE user_id = ?";
        try (PreparedStatement selectStmt = conn.prepareStatement(query)) {
            selectStmt.setString(1, trip.getUserId());
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                // Update existing achievement
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, achievement.getTotalCarbonSavedExp());
                    updateStmt.setInt(2, achievement.getTotalCalorieBurntExp());
                    updateStmt.setString(3, achievement.getCarbonSavedMedal());
                    updateStmt.setString(4, achievement.getCalorieBurntMedal());
                    updateStmt.setString(5, trip.getUserId());
                    updateStmt.executeUpdate();
                }
            } else {
                // Insert new achievement
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, trip.getUserId());
                    insertStmt.setInt(2, achievement.getTotalCarbonSavedExp());
                    insertStmt.setInt(3, achievement.getTotalCalorieBurntExp());
                    insertStmt.setString(4, achievement.getCarbonSavedMedal());
                    insertStmt.setString(5, achievement.getCalorieBurntMedal());
                    insertStmt.executeUpdate();
                }
            }
        }
    }
}
}
