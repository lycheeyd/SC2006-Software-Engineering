package com.example.Calowin;

import java.util.Map;

public class CurrentLocation {
    private String name;
    private double latitude;
    private double longitude;

    public CurrentLocation(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    // Getters and Setters
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

      // Method to create CurrentLocation from JSON
    public static CurrentLocation fromJson(Map<String, Object> json) {
        return new CurrentLocation(
            (String) json.get("name"),
            (Double) json.get("latitude"),
            (Double) json.get("longitude")
        );
    }
}
