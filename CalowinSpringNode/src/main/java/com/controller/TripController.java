package com.controller;

import java.util.Map;

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

    @Value("${trips.module.urlPrefix}")
    private String urlPrefix;

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

    @GetMapping("/api/keys/{keyName}")
    public ResponseEntity<?> getApiKey(@PathVariable String keyName) {
        // Forward view profile request to AccountModule
        try {
            String url = urlPrefix + "/api/keys/" + keyName;
            return restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException ex) {
            HttpStatusCode statusCode = ex.getStatusCode();
            if (statusCode == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(statusCode).body(ex.getMessage());
            } else {
                return ResponseEntity.status(statusCode).body(statusCode + ex.getMessage());
            }
        } catch (Exception ex)  {
            System.out.println(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

}

