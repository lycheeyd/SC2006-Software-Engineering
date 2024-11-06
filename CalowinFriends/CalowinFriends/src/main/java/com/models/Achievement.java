package com.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Achievement", schema = "dbo")
public class Achievement {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "total_carbon_saved")
    private int totalCarbonSaved;

    @Column(name = "total_calorie_burnt")
    private int totalCalorieBurnt;

    // Getters and Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTotalCarbonSaved() {
        return totalCarbonSaved;
    }

    public void setTotalCarbonSaved(int totalCarbonSaved) {
        this.totalCarbonSaved = totalCarbonSaved;
    }

    public int getTotalCalorieBurnt() {
        return totalCalorieBurnt;
    }

    public void setTotalCalorieBurnt(int totalCalorieBurnt) {
        this.totalCalorieBurnt = totalCalorieBurnt;
    }
}