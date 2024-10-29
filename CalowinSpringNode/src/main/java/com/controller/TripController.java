package com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.DataTransferObject.TripDTO.AchievementMatrixDTO;
import com.DataTransferObject.TripDTO.TripInfoDTO;


@RestController
@RequestMapping("/central/trips")
public class TripController extends HttpReqController{

    public TripController(RestTemplate restTemplate) {
        super(restTemplate);
    }

    // Implemenet you own mapping below

    @PostMapping("/addTripMetrics")
    public ResponseEntity<String> signup(@RequestBody AchievementMatrixDTO DTO) {
        // Forward signup request to AuthModule
        String url = "http://localhost:8082/achievements/addTripMetrics"; // URL of Auth Java application
        return restTemplate.postForEntity(url, DTO, String.class);
    }

    @PostMapping("/start")
    public ResponseEntity<String> signup(@RequestBody TripInfoDTO user) {
        // Forward signup request to AuthModule
        String url = "http://localhost:8082/trips/start"; // URL of Auth Java application
        return restTemplate.postForEntity(url, user, String.class);
    }


}

