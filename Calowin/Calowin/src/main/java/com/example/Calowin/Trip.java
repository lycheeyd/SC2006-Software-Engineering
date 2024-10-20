package com.example.Calowin;
import java.security.SecureRandom;



public class Trip {
    private CurrentLocation startLocation;
    private Location destination; // The destination is of type Location
    private TravelMethod travelMethod;
    private int caloriesBurnt;
    private int carbonSaved;
    private double distance;
    private String userId; // New field
    private final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // Alphanumeric characters
    private final SecureRandom random = new SecureRandom(); // For better randomness


    public Trip(CurrentLocation startLocation, Location destination, TravelMethod travelMethod) {
        this.startLocation = startLocation;
        this.destination = destination;
        this.travelMethod = travelMethod;
    }

    

     private String generateUserId() {
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            id.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return id.toString();
    }

    public String getUserId() {
        return userId;  // Getter for userId
    }

    public void setUserId(){
        this.userId = generateUserId();
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

    public void setDistance(double distance){
        this.distance = distance;
    }

    public double getDistance(){
        return distance;
    }

    public int getCarbonSaved() {
        return carbonSaved;
    }

    public void setCarbonSaved(int carbonSaved) {
        this.carbonSaved = carbonSaved;
    }
}
