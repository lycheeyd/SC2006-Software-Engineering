package com.example.Calowin;

public class Trip {
    private CurrentLocation startLocation;
    private Location destination; // The destination is of type Location
    private TravelMethod travelMethod;
    private int caloriesBurnt;
    private int carbonSaved;

    public Trip(CurrentLocation startLocation, Location destination, TravelMethod travelMethod) {
        this.startLocation = startLocation;
        this.destination = destination;
        this.travelMethod = travelMethod;
    }

    // Getters and Setters
    public CurrentLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(CurrentLocation startLocation) {
        this.startLocation = startLocation;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public TravelMethod getTravelMethod() {
        return travelMethod;
    }

    public void setTravelMethod(TravelMethod travelMethod) {
        this.travelMethod = travelMethod;
    }

    public int getCaloriesBurnt() {
        return caloriesBurnt;
    }

    public void setCaloriesBurnt(int caloriesBurnt) {
        this.caloriesBurnt = caloriesBurnt;
    }

    public int getCarbonSaved() {
        return carbonSaved;
    }

    public void setCarbonSaved(int carbonSaved) {
        this.carbonSaved = carbonSaved;
    }
}
