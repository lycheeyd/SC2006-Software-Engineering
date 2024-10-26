package com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;


public class WellnessZoneController extends HttpReqController{

        public WellnessZoneController(RestTemplate restTemplate) {
        super(restTemplate);
    }

    // Implemenet you own mapping below

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginDTO request) {
///        // Forward login request to AccountModule
///        String url = "http://localhost:8081/account/login";
///        return restTemplate.postForEntity(url, request, String.class);
///    }


}
