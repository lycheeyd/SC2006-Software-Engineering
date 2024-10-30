package com.WellnessZone;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HttpReqController {

    @GetMapping("/parks")
    public List<Map<String, Object>> getNearbyParks(@RequestParam("lat") double userLat, @RequestParam("lon") double userLon) {
        // Initialize the NParkExtracter with user coordinates
        NParkExtracter parkExtracter = new NParkExtracter(userLat, userLon);

        // Get the list of NPark objects
        List<NPark> parks = parkExtracter.getParks();

        // Reformat into List of HashMaps to send as a response
        List<Map<String, Object>> formattedParks = new ArrayList<>();

        for (NPark park : parks) {
            Map<String, Object> parkMap = new HashMap<>();
            parkMap.put("name", park.getName());
            parkMap.put("distance", park.getDistance());
            parkMap.put("closestPoint", park.getClosestPoint());

            formattedParks.add(parkMap);
        }

        return formattedParks;
    }
}
