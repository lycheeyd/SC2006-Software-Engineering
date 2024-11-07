package com.example.CalowinTrip;


public class Achievement {
    private int totalCarbonSavedExp;
    private int totalCalorieBurntExp;
    private String carbonSavedMedal;
    private String calorieBurntMedal;

    // Separate thresholds for carbon and calorie medals
    private final int CARBON_BRONZE_THRESHOLD = 1000;
    private final int CARBON_SILVER_THRESHOLD = 5000;
    private final int CARBON_GOLD_THRESHOLD = 10000;
    private final int CARBON_PLATINUM_THRESHOLD = 15000;

    private final int CALORIE_BRONZE_THRESHOLD = 1000;
    private final int CALORIE_SILVER_THRESHOLD = 5000;
    private final int CALORIE_GOLD_THRESHOLD = 10000;
    private final int CALORIE_PLATINUM_THRESHOLD = 15000;

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
        this.carbonSavedMedal = calculateCarbonMedal(totalCarbonSavedExp);
        this.calorieBurntMedal = calculateCalorieMedal(totalCalorieBurntExp);
    }

     // Helper method to calculate carbon medal based on experience
     private String calculateCarbonMedal(int exp) {
        if (exp >= CARBON_PLATINUM_THRESHOLD) {
            return "CarbonPlatinum";
        } else if (exp >= CARBON_GOLD_THRESHOLD) {
            return "CarbonGold";
        } else if (exp >= CARBON_SILVER_THRESHOLD) {
            return "CarbonSilver";
        } else if (exp >= CARBON_BRONZE_THRESHOLD) {
            return "CarbonBronze";
        } else {
            return "No Medal";
        }
    }

    // Helper method to calculate calorie medal based on experience
    private String calculateCalorieMedal(int exp) {
        if (exp >= CALORIE_PLATINUM_THRESHOLD) {
            return "CaloriePlatinum";
        } else if (exp >= CALORIE_GOLD_THRESHOLD) {
            return "CalorieGold";
        } else if (exp >= CALORIE_SILVER_THRESHOLD) {
            return "CalorieSilver";
        } else if (exp >= CALORIE_BRONZE_THRESHOLD) {
            return "CalorieBronze";
        } else {
            return "No Medal";
        }
    }

    public int getCarbonBronzeThreshold() {
        return CARBON_BRONZE_THRESHOLD;
    }

    public int getCarbonSilverThreshold() {
        return CARBON_SILVER_THRESHOLD;
    }

    public int getCarbonGoldThreshold() {
        return CARBON_GOLD_THRESHOLD;
    }

    public int getCarbonPlatinumThreshold() {
        return CARBON_PLATINUM_THRESHOLD;
    }

    public int getCalorieBronzeThreshold() {
        return CALORIE_BRONZE_THRESHOLD;
    }

    public int getCalorieSilverThreshold() {
        return CALORIE_SILVER_THRESHOLD;
    }

    public int getCalorieGoldThreshold() {
        return CALORIE_GOLD_THRESHOLD;
    }

    public int getCaloriePlatinumThreshold() {
        return CALORIE_PLATINUM_THRESHOLD;
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

    public void setTotalCarbonSavedExp(int totalCarbonSavedExp) {
        this.totalCarbonSavedExp = totalCarbonSavedExp;
    }

    public void setTotalCalorieBurntExp(int totalCalorieBurntExp) {
        this.totalCalorieBurntExp = totalCalorieBurntExp;
    }
    
    public void setCarbonSavedMedal(String carbonSavedMedal) {
        this.carbonSavedMedal = carbonSavedMedal;
    }

    public void setCalorieBurntMedal(String calorieBurntMedal) {
        this.calorieBurntMedal = calorieBurntMedal;
    }

    
}
