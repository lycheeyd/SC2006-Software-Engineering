package com.example.CalowinTrip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/achievements")
public class AchievementController {

    // Removed static achievement, to store achievement data per user
    // You should fetch the Achievement data from the database when a user makes a request
    private final Achievement achievement = new Achievement();

    // Endpoint to add trip metrics (carbon saved and calories burnt)
    @PostMapping("/addTripMetrics")
    public void addTripMetrics(@RequestParam int carbonSaved, @RequestParam int caloriesBurnt, @RequestBody Trip trip) {
        // Fetch the current user's achievement from the database
        Achievement userAchievement = getUserAchievement(trip.getUserId());

        // Update the achievement with the trip metrics (carbon saved and calories burnt)
        userAchievement.addTripExperience(carbonSaved, caloriesBurnt);
        
        // Save or update the user's achievement in the database using the trip's user ID
        try { 
            saveOrUpdateAchievement(userAchievement, trip); // Pass user-specific achievement data
        } catch (SQLException e) {
            // Handle the exception (log it, return an error response, etc.)
            e.printStackTrace();
        }
    }

    // Fetch the user's achievement data from the database
    private Achievement getUserAchievement(String userId) {
        Achievement userAchievement = new Achievement();
    
    // SQL query to fetch achievement data for the user
    String selectQuery = "SELECT total_carbon_saved, total_calorie_burnt, carbon_medal, calorie_medal FROM achievement WHERE user_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(selectQuery)) {

        // Set the user ID parameter in the query
        stmt.setString(1, userId);

        // Execute the query
        ResultSet rs = stmt.executeQuery();

        // If the user exists, populate the Achievement object
        if (rs.next()) {
            userAchievement.setTotalCarbonSavedExp(rs.getInt("total_carbon_saved"));
            userAchievement.setTotalCalorieBurntExp(rs.getInt("total_calorie_burnt"));
            userAchievement.setCarbonSavedMedal(rs.getString("carbon_medal"));
            userAchievement.setCalorieBurntMedal(rs.getString("calorie_medal"));
        } else {
            // If no data found, return a default Achievement object (0s and empty strings)
            System.out.println("No achievement record found for user: " + userId);
        }
    } catch (SQLException e) {
        // Handle exceptions (e.g., log the error)
        System.out.println("Error retrieving achievement data for user: " + userId);
        e.printStackTrace();
    }

    return userAchievement;
    }

    // Endpoint to get current achievement progress (EXP and medals)
    @GetMapping("/progress")
    public AchievementResponse getAchievementProgress(@RequestParam String userId) {
        Achievement userAchievement = getUserAchievement(userId); // Fetch user-specific achievement data
        return new AchievementResponse(
            userAchievement.getTotalCarbonSavedExp(),
            userAchievement.getTotalCalorieBurntExp(),
            userAchievement.getCarbonSavedMedal(),
            userAchievement.getCalorieBurntMedal()
        );
    }

    private void saveOrUpdateAchievement(Achievement userAchievement, Trip trip) throws SQLException {
        String selectQuery = "SELECT * FROM achievement WHERE user_id = ?";
        String updateQuery = "UPDATE achievement SET total_carbon_saved = ?, total_calorie_burnt = ?, carbon_medal = ?, calorie_medal = ? WHERE user_id = ?";
        String insertQuery = "INSERT INTO achievement (user_id, total_carbon_saved, total_calorie_burnt, carbon_medal, calorie_medal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {

            // Check if the user already has an entry by user_id
            selectStmt.setString(1, trip.getUserId());  // Fetch user ID from the Trip object
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                // User exists, update the existing record
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, userAchievement.getTotalCarbonSavedExp());
                    updateStmt.setInt(2, userAchievement.getTotalCalorieBurntExp());
                    updateStmt.setString(3, userAchievement.getCarbonSavedMedal());
                    updateStmt.setString(4, userAchievement.getCalorieBurntMedal());
                    updateStmt.setString(5, trip.getUserId());  // Use trip.getUserId() here to target the user
                    updateStmt.executeUpdate();
                    
                    // Log success
                    System.out.println("Record updated successfully for user " + trip.getUserId());
                }
            } else {
                // No record found, insert a new record
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, trip.getUserId());  // Use trip.getUserId() to insert the new record
                    insertStmt.setInt(2, userAchievement.getTotalCarbonSavedExp());
                    insertStmt.setInt(3, userAchievement.getTotalCalorieBurntExp());
                    insertStmt.setString(4, userAchievement.getCarbonSavedMedal());
                    insertStmt.setString(5, userAchievement.getCalorieBurntMedal());
                    insertStmt.executeUpdate();
                    
                    // Log success
                    System.out.println("New record inserted for user " + trip.getUserId());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while saving or updating achievement for user " + trip.getUserId());
            throw e;  // Re-throw the exception to be handled at a higher level
        }
    }

    // Response class to encapsulate achievement data
    public class AchievementResponse {
        private int totalCarbonSavedExp;
        private int totalCalorieBurntExp;
        private String carbonSavedMedal;
        private String calorieBurntMedal;

        public AchievementResponse(int totalCarbonSavedExp, int totalCalorieBurntExp, String carbonSavedMedal,
                       String calorieBurntMedal) {
            this.totalCarbonSavedExp = totalCarbonSavedExp;
            this.totalCalorieBurntExp = totalCalorieBurntExp;
            this.carbonSavedMedal = carbonSavedMedal;
            this.calorieBurntMedal = calorieBurntMedal;
        }

        // Getters for the response fields
        public int getTotalCarbonSavedExp() {
            return totalCarbonSavedExp;
        }

        public int getTotalCalorieBurntExp() {
            return totalCalorieBurntExp;
        }

        public String getCarbonSavedMedal() {
            return carbonSavedMedal;
        }

        public String getCalorieBurntMedal() {
            return calorieBurntMedal;
        }
    }
}
