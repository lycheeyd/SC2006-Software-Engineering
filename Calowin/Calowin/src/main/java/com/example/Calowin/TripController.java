package com.example.Calowin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @GetMapping("/locations")
    public List<Location> getAvailableLocations() {
        return getPredefinedLocations();
    }

    @GetMapping("/current-location")
    public ResponseEntity<CurrentLocation> getCurrentLocation() {
    CurrentLocation currentLocation = new CurrentLocation("KFC", 1.3521, 103.8198);
    return ResponseEntity.ok(currentLocation);
}

    // This method returns a list of predefined locations
    private List<Location> getPredefinedLocations() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location("Bedok", 1.3216, 103.9335)); // Latitude and Longitude for Bedok
        locations.add(new Location("Jurong East", 1.3340, 103.7432)); // Latitude and Longitude for Jurong East
        locations.add(new Location("NTU", 1.3453, 103.6831)); // Latitude and Longitude for Nanyang Technological University (NTU)
        return locations;
    }

    @GetMapping("/methods")
    public List<TravelMethod> getTravelMethods() {
        return Arrays.asList(TravelMethod.values());
    }

    @PostMapping("/start")
    public Trip startTrip(@RequestBody Trip trip) {
    // Get the current location of the user
    ResponseEntity<CurrentLocation> response = getCurrentLocation(); // Fetching the current location
    CurrentLocation userLocation = response.getBody(); // Extracting the CurrentLocation from the ResponseEntity

    // Check if the user location is not null
    if (userLocation == null) {
        throw new RuntimeException("Unable to retrieve current location."); // Handle the error appropriately
    }

    // Calculate the distance to the selected destination
    double distance = calculateDistance(userLocation, trip.getDestination());

    // Calculate calories burned and carbon saved based on the travel method and distance
    int caloriesBurned = calculateCalories(trip.getTravelMethod(), distance);
    int carbonSaved = calculateCarbon(trip.getTravelMethod(), distance);


    // Set the calculated values in the trip object
    trip.setCaloriesBurnt(caloriesBurned);
    trip.setCarbonSaved(carbonSaved);
    trip.setDistance(distance);

    // Return the updated trip object with metrics
    return trip;
}


    private double calculateDistance(CurrentLocation userLocation, Location destination) {
        double earthRadius = 6371; // Radius in kilometers
        double dLat = toRadians(destination.getLatitude() - userLocation.getLatitude());
        double dLon = toRadians(destination.getLongitude() - userLocation.getLongitude());

        // Calculate the distance between two geographical points using the Haversine formula
        double a = sin(dLat / 2) * sin(dLat / 2) + // Calculate the square of half the chord length between the points
        cos(toRadians(userLocation.getLatitude())) * // Calculate the cosine of the user's latitude
        cos(toRadians(destination.getLatitude())) * // Calculate the cosine of the destination's latitude
        sin(dLon / 2) * sin(dLon / 2); // Calculate the square of half the difference in longitude

        // Calculate the angular distance in radians
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));

        // Return the distance by multiplying the angular distance by the Earth's radius
        return earthRadius * c; // distance in kilometers

    }

    private int calculateCalories(TravelMethod method, double distance) {
        switch (method) {
            case WALK:
                return (int) (distance * 50); // Example: 50 calories per km
            case CYCLE:
                return (int) (distance * 30); // Example: 30 calories per km
            case PUBLIC_TRANSPORT:
                return (int) (distance * 10); // Example: 10 calories per km
            case CAR:
                return 0; // Example: No calories burned while riding
            default:
                return 0;
        }
    }

    private int calculateCarbon(TravelMethod method, double distance) {
        switch (method) {
            case WALK:
                return (int) (distance * 2); // Example: 2 kg per km
            case CYCLE:
                return (int) (distance * 2); // Example: 2 kg per km
            case PUBLIC_TRANSPORT:
                return 0;
            case CAR:
                return 0;
            default:
                return 0;
        }
    }
}
