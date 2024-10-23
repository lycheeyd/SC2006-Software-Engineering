package com.example.Calowin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Calowin.DataTransferObject.CurrentLocationDTO;
import com.example.Calowin.DataTransferObject.LocationDTO;
import com.example.Calowin.DataTransferObject.TripDTO;

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
    public List<LocationDTO> getAvailableLocations() {
        return getPredefinedLocations();
    }

    @GetMapping("/current-location")
    public ResponseEntity<CurrentLocationDTO> getCurrentLocation() {
        CurrentLocationDTO currentLocation = new CurrentLocationDTO();
        currentLocation.setName("KFC");
        currentLocation.setLatitude(1.3521);
        currentLocation.setLongitude(103.8198);
        return ResponseEntity.ok(currentLocation);
    }

    private List<LocationDTO> getPredefinedLocations() {
        List<LocationDTO> locations = new ArrayList<>();
        locations.add(createLocationDTO("Bedok", 1.3216, 103.9335));
        locations.add(createLocationDTO("Jurong East", 1.3340, 103.7432));
        locations.add(createLocationDTO("NTU", 1.3453, 103.6831));
        return locations;
    }

    private LocationDTO createLocationDTO(String name, double latitude, double longitude) {
        LocationDTO location = new LocationDTO();
        location.setName(name);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    @PostMapping("/start")
    public TripDTO startTrip(@RequestBody TripDTO tripDTO) {
        ResponseEntity<CurrentLocationDTO> response = getCurrentLocation();
        CurrentLocationDTO userLocation = response.getBody();

        if (userLocation == null) {
            throw new RuntimeException("Unable to retrieve current location.");
        }

        // Calculate the distance, calories burnt, and carbon saved
        double distance = calculateDistance(userLocation, tripDTO);
        int caloriesBurned = calculateCalories(tripDTO.getTravelMethod(), distance);
        int carbonSaved = calculateCarbon(tripDTO.getTravelMethod(), distance);

        // Set calculated values
        tripDTO.setCaloriesBurnt(caloriesBurned);
        tripDTO.setCarbonSaved(carbonSaved);
        tripDTO.setDistance(distance);

        // Insert trip data into the database
        insertTripIntoDatabase(tripDTO, userLocation);

        return tripDTO;
    }

    private double calculateDistance(CurrentLocationDTO userLocation, TripDTO tripDTO) {
        double earthRadius = 6371;
        double dLat = Math.toRadians(tripDTO.getDestinationLatitude() - userLocation.getLatitude());
        double dLon = Math.toRadians(tripDTO.getDestinationLongitude() - userLocation.getLongitude());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(userLocation.getLatitude())) *
                Math.cos(Math.toRadians(tripDTO.getDestinationLatitude())) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    private int calculateCalories(String method, double distance) {
        switch (method) {
            case "WALK":
                return (int) (distance * 50);
            case "CYCLE":
                return (int) (distance * 30);
            case "PUBLIC_TRANSPORT":
                return (int) (distance * 10);
            case "CAR":
                return 0;
            default:
                return 0;
        }
    }

    private int calculateCarbon(String method, double distance) {
        switch (method) {
            case "WALK":
                return (int) (distance * 2);
            case "CYCLE":
                return (int) (distance * 2);
            case "PUBLIC_TRANSPORT":
                return 0;
            case "CAR":
                return 0;
            default:
                return 0;
        }
    }

    private void insertTripIntoDatabase(TripDTO tripDTO, CurrentLocationDTO startLocation) {
        String insertSQL = "INSERT INTO trips (trip_id, start_location, start_longitude, "
                + "start_latitude, end_location, end_latitude, "
                + "end_longitude, distance, calories_burnt, carbon_saved, trip_time, travel_method, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            String tripId = generateUniqueTripId(); // Generate a random trip ID
            preparedStatement.setString(1, tripId);
            preparedStatement.setString(2, startLocation.getName());
            preparedStatement.setDouble(3, startLocation.getLongitude());
            preparedStatement.setDouble(4, startLocation.getLatitude());
            preparedStatement.setString(5, tripDTO.getDestinationName());
            preparedStatement.setDouble(6, tripDTO.getDestinationLatitude());
            preparedStatement.setDouble(7, tripDTO.getDestinationLongitude());
            preparedStatement.setDouble(8, tripDTO.getDistance());
            preparedStatement.setInt(9, tripDTO.getCaloriesBurnt());
            preparedStatement.setInt(10, tripDTO.getCarbonSaved());
            preparedStatement.setTimestamp(11, getCurrentSqlTimestamp());
            preparedStatement.setString(12, tripDTO.getTravelMethod());
            preparedStatement.setString(13, "ONGOING");

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Trip data inserted successfully.");
            } else {
                System.out.println("No trip data was inserted.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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

