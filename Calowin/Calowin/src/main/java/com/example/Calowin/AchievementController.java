package com.example.Calowin;

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

