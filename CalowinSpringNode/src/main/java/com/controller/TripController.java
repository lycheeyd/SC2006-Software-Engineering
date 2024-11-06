package com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.DataTransferObject.TripDTO.CurrentLocation;
import com.DataTransferObject.TripDTO.Location;
import com.DataTransferObject.TripDTO.Achievement;
import com.DataTransferObject.TripDTO.TripInfoDTO;



@RestController
@RequestMapping("/central/trips")
public class TripController extends HttpReqController{

    public TripController(RestTemplate restTemplate) {
        super(restTemplate);
    }

        @GetMapping("/methods")
    public List<Map<String, Object>> getTravelMethods() {
        // Construct the URL for the backend trip controller
        String url = "http://localhost:8082/trips/methods";
        
        // Make the request and return the result
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                url, 
                HttpMethod.GET, 
                null, 
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );
        return response.getBody();
    }


    @PostMapping("/start")
    public ResponseEntity<String> startTrip(@RequestBody TripInfoDTO tripInfo) {
        // Define the backend URL for starting the trip
        String url = "http://localhost:8082/trips/start"; // URL for backend /start endpoint

        // Send the request to the backend with the trip data
        ResponseEntity<String> response = restTemplate.exchange(
                url, 
                HttpMethod.POST, 
                new HttpEntity<>(tripInfo), 
                String.class
        );
        // Return the response from the backend
        return response;
    }   

    @PostMapping("/addTripMetrics")
    public ResponseEntity<String> addTripMetrics(@RequestParam int carbonSaved, @RequestParam int caloriesBurnt, TripInfoDTO trip) {
        // Define the backend URL for starting the trip
        String url = "http://localhost:8082/achievement/start"; // URL for backend /start endpoint

        // Send the request to the backend with the trip data
        ResponseEntity<String> response = restTemplate.exchange(
                url, 
                HttpMethod.POST, 
                new HttpEntity<>(trip), 
                String.class
        );
        // Return the response from the backend
        return response;
    }   

    @GetMapping("/progress")
    public ResponseEntity<Achievement> getAchievementProgress() {
        // Construct the URL to call the external achievement service
        String url = "http://localhost:8082/achievements/progress";

        // Use RestTemplate to make the GET request to the external service
        ResponseEntity<Achievement> response = restTemplate.exchange(
            url, 
            HttpMethod.GET, 
            null, 
            Achievement.class);

        // Check if the response is successful
        if (response.getStatusCode().is2xxSuccessful()) {
            // Return the response body from the external service
            return ResponseEntity.ok(response.getBody());
        } else {
            // Handle failure (e.g., service unavailable, invalid response)
            return ResponseEntity.status(500).body(null);
        }
    }



}


    


