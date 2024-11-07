package com.example.CalowinTrip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/achievements")
public class AchievementController {

    private Achievement achievement;

    public AchievementController() {
        this.achievement = new Achievement();
    }

    // Endpoint to add trip metrics (carbon saved and calories burnt)
    @PostMapping("/addTripMetrics")
    public void addTripMetrics(@RequestParam int carbonSaved, @RequestParam int caloriesBurnt, Trip trip) {
        achievement.addTripExperience(carbonSaved, caloriesBurnt);
        // Save or update the user's achievement in the database
    try {
        saveOrUpdateAchievement(trip); // Pass the userId as a parameter
    } catch (SQLException e) {
        // Handle the exception (log it, return an error response, etc.)
        e.printStackTrace();
    }


    }

    // Endpoint to get current achievement progress (EXP and medals)
    @GetMapping("/progress")
    public AchievementResponse getAchievementProgress(String userId) {
       

        return new AchievementResponse(

            achievement.getTotalCarbonSavedExp(),
            achievement.getTotalCalorieBurntExp(),
            achievement.getCarbonSavedMedal(),
            achievement.getCalorieBurntMedal()
        );
    }

    private void saveOrUpdateAchievement(Trip trip) throws SQLException {
        
        String selectQuery = "SELECT * FROM achievement WHERE user_id = ?";
        String updateQuery = "UPDATE achievement SET total_carbon_saved = ?, total_calorie_burnt = ?, carbon_medal = ?, calorie_medal = ? WHERE user_id = ?";
        String insertQuery = "INSERT INTO achievement (user_id, total_carbon_saved, total_calorie_burnt, carbon_medal, calorie_medal) VALUES (?, ?, ?, ?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
    
            // Check if the user already has an entry
            selectStmt.setString(1, trip.getUserId());
            ResultSet rs = selectStmt.executeQuery();
    
            if (rs.next()) {
                // User exists, so update the existing record
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, achievement.getTotalCarbonSavedExp());
                    updateStmt.setInt(2, achievement.getTotalCalorieBurntExp());
                    updateStmt.setString(3, achievement.getCarbonSavedMedal());
                    updateStmt.setString(4, achievement.getCalorieBurntMedal());
                    updateStmt.setString(5, trip.getUserId());
                    updateStmt.executeUpdate();
                    
                    // Log to the console
                    System.out.println("Record updated successfully for user " + trip.getUserId());
                }
            } else {
                // Insert new record
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    
                    insertStmt.setString(1, trip.getUserId());
                    insertStmt.setInt(2, achievement.getTotalCarbonSavedExp());
                    insertStmt.setInt(3, achievement.getTotalCalorieBurntExp());
                    insertStmt.setString(4, achievement.getCarbonSavedMedal());
                    insertStmt.setString(5, achievement.getCalorieBurntMedal());
                    insertStmt.executeUpdate();
                    
                    // Log to the console
                    System.out.println("New record inserted for user " + trip.getUserId());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while saving or updating achievement for user " + trip.getUserId());
            throw e;  // Re-throw the exception to be handled at a higher level
        }
    }
    
    
    // Response class to encapsulate achievement data
    public static class AchievementResponse {
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

