package com.example.CalowinTrip;


public class Achievement {
    private int totalCarbonSavedExp;
    private int totalCalorieBurntExp;
    private String carbonSavedMedal;
    private String calorieBurntMedal;

    private final int BRONZE_THRESHOLD = 1000;
    private final int SILVER_THRESHOLD = 5000;
    private final int GOLD_THRESHOLD = 10000;

    public Achievement() {
        this.totalCarbonSavedExp = 0; // Default value
        this.totalCalorieBurntExp = 0; // Default value
        this.carbonSavedMedal = "No Medal"; // Default value
        this.calorieBurntMedal = "No Medal"; // Default value
    }

    // Method to add experience from a specific trip
    public void addTripExperience(int carbonSaved, int caloriesBurnt) {
        this.totalCarbonSavedExp += carbonSaved;
        this.totalCalorieBurntExp += caloriesBurnt;
        updateMedalStatus();
    }

    // Method to check and update medal status
    private void updateMedalStatus() {
        this.carbonSavedMedal = calculateMedal(totalCarbonSavedExp);
        this.calorieBurntMedal = calculateMedal(totalCalorieBurntExp);
    }

    // Helper method to calculate medal based on experience
    private String calculateMedal(int exp) {
        if (exp >= GOLD_THRESHOLD) {
            return "Gold";
        } else if (exp >= SILVER_THRESHOLD) {
            return "Silver";
        } else if (exp >= BRONZE_THRESHOLD) {
            return "Bronze";
        } else {
            return "No Medal";
        }
    }

    // New methods to calculate points needed for the next medal
    public int pointsToNextCarbonBronze() {
        return BRONZE_THRESHOLD - totalCarbonSavedExp > 0 ? BRONZE_THRESHOLD - totalCarbonSavedExp : 0;
    }

    public int pointsToNextCarbonSilver() {
        return SILVER_THRESHOLD - totalCarbonSavedExp > 0 ? SILVER_THRESHOLD - totalCarbonSavedExp : 0;
    }

    public int pointsToNextCarbonGold() {
        return GOLD_THRESHOLD - totalCarbonSavedExp > 0 ? GOLD_THRESHOLD - totalCarbonSavedExp : 0;
    }

    public int pointsToNextCalorieBronze() {
        return BRONZE_THRESHOLD - totalCalorieBurntExp > 0 ? BRONZE_THRESHOLD - totalCalorieBurntExp : 0;
    }

    public int pointsToNextCalorieSilver() {
        return SILVER_THRESHOLD - totalCalorieBurntExp > 0 ? SILVER_THRESHOLD - totalCalorieBurntExp : 0;
    }

    public int pointsToNextCalorieGold() {
        return GOLD_THRESHOLD - totalCalorieBurntExp > 0 ? GOLD_THRESHOLD - totalCalorieBurntExp : 0;
    }

    // Getters for thresholds
    public final int BronzeThreshold() {
        return BRONZE_THRESHOLD;
    }

    public final int SilverThreshold() {
        return SILVER_THRESHOLD;
    }

    public final int GoldThreshold() {
        return GOLD_THRESHOLD;
    }



    // Getters for the frontend to access medal statuses and experience points
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
