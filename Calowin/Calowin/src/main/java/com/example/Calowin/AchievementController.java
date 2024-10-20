package com.example.Calowin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import org.apache.el.stream.Optional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/achievements")
public class AchievementController {

    private final Achievement achievement;

    public AchievementController() {
        this.achievement = new Achievement();
    }

    // Endpoint to add trip metrics (carbon saved and calories burnt)
    @PostMapping("/addTripMetrics")
    public void addTripMetrics(@RequestParam int carbonSaved, @RequestParam int caloriesBurnt) {
        achievement.addTripExperience(carbonSaved, caloriesBurnt);
        // Save or update the user's achievement in the database
    try {
        saveOrUpdateAchievement("ABC"); // Pass the userId as a parameter
    } catch (SQLException e) {
        // Handle the exception (log it, return an error response, etc.)
        e.printStackTrace();
    }


    }

    // Endpoint to get current achievement progress (EXP and medals)
    @GetMapping("/progress")
    public AchievementResponse getAchievementProgress() {
        return new AchievementResponse(
            achievement.getTotalCarbonSavedExp(),
            achievement.getTotalCalorieBurntExp(),
            achievement.getCarbonSavedMedal(),
            achievement.getCalorieBurntMedal(),
            achievement.pointsToNextCarbonBronze(),
            achievement.pointsToNextCarbonSilver(),
            achievement.pointsToNextCarbonGold(),
            achievement.pointsToNextCalorieBronze(),
            achievement.pointsToNextCalorieSilver(),
            achievement.pointsToNextCalorieGold()
        );
    }

    private void saveOrUpdateAchievement(String userId) throws SQLException {
        
        String selectQuery = "SELECT * FROM achievement WHERE user_id = ?";
        String updateQuery = "UPDATE achievement SET total_carbon_saved = ?, total_calorie_burnt = ?, carbon_medal = ?, calorie_medal = ? WHERE user_id = ?";
        String insertQuery = "INSERT INTO achievement (user_id, total_carbon_saved, total_calorie_burnt, carbon_medal, calorie_medal) VALUES (?, ?, ?, ?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
    
            // Check if the user already has an entry
            selectStmt.setString(1, userId);
            ResultSet rs = selectStmt.executeQuery();
    
            if (rs.next()) {
                // User exists, so update the existing record
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, achievement.getTotalCarbonSavedExp());
                    updateStmt.setInt(2, achievement.getTotalCalorieBurntExp());
                    updateStmt.setString(3, achievement.getCarbonSavedMedal());
                    updateStmt.setString(4, achievement.getCalorieBurntMedal());
                    updateStmt.setString(5, userId);
                    updateStmt.executeUpdate();
                    
                    // Log to the console
                    System.out.println("Record updated successfully for user " + userId);
                }
            } else {
                // Insert new record
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, userId);
                    insertStmt.setInt(2, achievement.getTotalCarbonSavedExp());
                    insertStmt.setInt(3, achievement.getTotalCalorieBurntExp());
                    insertStmt.setString(4, achievement.getCarbonSavedMedal());
                    insertStmt.setString(5, achievement.getCalorieBurntMedal());
                    insertStmt.executeUpdate();
                    
                    // Log to the console
                    System.out.println("New record inserted for user " + userId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while saving or updating achievement for user " + userId);
            throw e;  // Re-throw the exception to be handled at a higher level
        }
    }
    



    // Response class to encapsulate achievement data
    public static class AchievementResponse {
        private int totalCarbonSavedExp;
        private int totalCalorieBurntExp;
        private String carbonSavedMedal;
        private String calorieBurntMedal;
        private int pointsToNextCarbonBronze;
        private int pointsToNextCarbonSilver;
        private int pointsToNextCarbonGold;
        private int pointsToNextCalorieBronze;
        private int pointsToNextCalorieSilver;
        private int pointsToNextCalorieGold;

        public AchievementResponse(int totalCarbonSavedExp, int totalCalorieBurntExp, String carbonSavedMedal,
                                   String calorieBurntMedal, int pointsToNextCarbonBronze, int pointsToNextCarbonSilver,
                                   int pointsToNextCarbonGold, int pointsToNextCalorieBronze, int pointsToNextCalorieSilver,
                                   int pointsToNextCalorieGold) {
            this.totalCarbonSavedExp = totalCarbonSavedExp;
            this.totalCalorieBurntExp = totalCalorieBurntExp;
            this.carbonSavedMedal = carbonSavedMedal;
            this.calorieBurntMedal = calorieBurntMedal;
            this.pointsToNextCarbonBronze = pointsToNextCarbonBronze;
            this.pointsToNextCarbonSilver = pointsToNextCarbonSilver;
            this.pointsToNextCarbonGold = pointsToNextCarbonGold;
            this.pointsToNextCalorieBronze = pointsToNextCalorieBronze;
            this.pointsToNextCalorieSilver = pointsToNextCalorieSilver;
            this.pointsToNextCalorieGold = pointsToNextCalorieGold;
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

        public int getPointsToNextCarbonBronze() {
            return pointsToNextCarbonBronze;
        }

        public int getPointsToNextCarbonSilver() {
            return pointsToNextCarbonSilver;
        }

        public int getPointsToNextCarbonGold() {
            return pointsToNextCarbonGold;
        }

        public int getPointsToNextCalorieBronze() {
            return pointsToNextCalorieBronze;
        }

        public int getPointsToNextCalorieSilver() {
            return pointsToNextCalorieSilver;
        }

        public int getPointsToNextCalorieGold() {
            return pointsToNextCalorieGold;
        }


        
    }

}

