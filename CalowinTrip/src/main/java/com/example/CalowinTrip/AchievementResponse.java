package com.example.CalowinTrip;

public class AchievementResponse {
    private int totalCarbonSavedExp;
    private int totalCalorieBurntExp;
    private String carbonSavedMedal;
    private String calorieBurntMedal;

    // Constructor to initialize the fields
    public AchievementResponse(int totalCarbonSavedExp, int totalCalorieBurntExp, String carbonSavedMedal, String calorieBurntMedal) {
        this.totalCarbonSavedExp = totalCarbonSavedExp;
        this.totalCalorieBurntExp = totalCalorieBurntExp;
        this.carbonSavedMedal = carbonSavedMedal;
        this.calorieBurntMedal = calorieBurntMedal;
    }

    // Getters for the fields
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

