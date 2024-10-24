package com.example.CalowinTrip;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.lang.Math.*;
import java.sql.ResultSet;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.sql.Timestamp;

@RestController
@RequestMapping("/api/trips")
public class TripController {



    public static Timestamp getCurrentSqlTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        return Timestamp.valueOf(now); // Convert LocalDateTime to Timestamp
    }

    @GetMapping("/locations")
    public List<Location> getAvailableLocations() {
        return getPredefinedLocations();
    }

    @GetMapping("/current-location")
    public ResponseEntity<CurrentLocation> getCurrentLocation() {
        CurrentLocation currentLocation = new CurrentLocation("KFC", 1.3521, 103.8198);
        return ResponseEntity.ok(currentLocation);
    }

    private List<Location> getPredefinedLocations() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location("Bedok", 1.3216, 103.9335));
        locations.add(new Location("Jurong East", 1.3340, 103.7432));
        locations.add(new Location("NTU", 1.3453, 103.6831));
        return locations;
    }

    @GetMapping("/methods")
    public List<TravelMethod> getTravelMethods() {
        return Arrays.asList(TravelMethod.values());
    }

    @PostMapping("/start")
    public Trip startTrip(@RequestBody Trip trip) {
        ResponseEntity<CurrentLocation> response = getCurrentLocation();
        CurrentLocation userLocation = response.getBody();

        if (userLocation == null) {
            throw new RuntimeException("Unable to retrieve current location.");
        }

        // Calculate the distance, calories burnt, and carbon saved
        double distance = calculateDistance(userLocation, trip.getDestination());
        int caloriesBurned = calculateCalories(trip.getTravelMethod(), distance);
        int carbonSaved = calculateCarbon(trip.getTravelMethod(), distance);

        // Set calculated values
        trip.setCaloriesBurnt(caloriesBurned);
        trip.setCarbonSaved(carbonSaved);
        trip.setDistance(distance);

        // Insert trip data into the database
        insertTripIntoDatabase(trip, userLocation);

        return trip;
    }

    private double calculateDistance(CurrentLocation userLocation, Location destination) {
        double earthRadius = 6371;
        double dLat = toRadians(destination.getLatitude() - userLocation.getLatitude());
        double dLon = toRadians(destination.getLongitude() - userLocation.getLongitude());

        double a = sin(dLat / 2) * sin(dLat / 2) +
                   cos(toRadians(userLocation.getLatitude())) *
                   cos(toRadians(destination.getLatitude())) *
                   sin(dLon / 2) * sin(dLon / 2);

        double c = 2 * atan2(sqrt(a), sqrt(1 - a));
        return earthRadius * c;
    }

    private int calculateCalories(TravelMethod method, double distance) {
        switch (method) {
            case WALK:
                return (int) (distance * 50);
            case CYCLE:
                return (int) (distance * 30);
            case PUBLIC_TRANSPORT:
                return (int) (distance * 10);
            case CAR:
                return 0;
            default:
                return 0;
        }
    }

    private int calculateCarbon(TravelMethod method, double distance) {
        switch (method) {
            case WALK:
                return (int) (distance * 2);
            case CYCLE:
                return (int) (distance * 2);
            case PUBLIC_TRANSPORT:
                return 0;
            case CAR:
                return 0;
            default:
                return 0;
        }
    }


    private void insertTripIntoDatabase(Trip trip, CurrentLocation startLocation) {
        String insertSQL = "INSERT INTO trips (trip_id, start_location, start_longitude, "
                         + "start_latitude, end_location, end_latitude, "
                         + "end_longitude, distance, calories_burnt, carbon_saved, trip_time, travel_method, status)"
                         + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // Ensure correct count
    
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
    
            // Generate a random trip ID
            String tripId = generateUniqueTripId().toString(); // Generate a random UUID
            Timestamp currentTimestamp = getCurrentSqlTimestamp();
            preparedStatement.setString(1, tripId); // Set the trip ID
            
            // Set values for the insert query
            preparedStatement.setString(2, startLocation.getName());
            preparedStatement.setDouble(3, startLocation.getLatitude());
            preparedStatement.setDouble(4, startLocation.getLongitude());
            preparedStatement.setString(5, trip.getDestination().getName());
            preparedStatement.setDouble(6, trip.getDestination().getLatitude());
            preparedStatement.setDouble(7, trip.getDestination().getLongitude());
            preparedStatement.setDouble(8, trip.getDistance());
            preparedStatement.setInt(9, trip.getCaloriesBurnt());
            preparedStatement.setInt(10, trip.getCarbonSaved()); 
            preparedStatement.setTimestamp(11, getCurrentSqlTimestamp());
            preparedStatement.setString(12, trip.getTravelMethod().toString()); // Store enum as string
            preparedStatement.setString(13, "ONGOING"); // Set status
    
            // Execute the insert statement
            int rowsAffected = preparedStatement.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Trip data inserted successfully.");
            } else {
                System.out.println("No trip data was inserted.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            throw new RuntimeException("Error inserting trip data into the database.", e);
        }
        
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int TRIP_ID_LENGTH = 6;
    private SecureRandom random = new SecureRandom();

    // Method to generate a random unique trip ID
    private String generateUniqueTripId() {
        String tripId;
        do {
            tripId = generateRandomString();
        } while (tripIdExists(tripId)); // Check if the trip ID already exists
        return tripId;
    }

    // Method to generate a random alphanumeric string
    private String generateRandomString() {
        StringBuilder sb = new StringBuilder(TRIP_ID_LENGTH);
        for (int i = 0; i < TRIP_ID_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    // Method to check if a trip ID already exists in the database
    private boolean tripIdExists(String tripId) {
        // Logic to query the database to check for existing trip ID
        // For example:
        String query = "SELECT COUNT(*) FROM trips WHERE trip_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, tripId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // If count > 0, the ID exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
        
}

