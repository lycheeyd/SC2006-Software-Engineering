package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.models.Achievement;
import com.service.LeaderboardService;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    @Autowired
    private LeaderboardService service;

    @GetMapping("/carbon")
    public List<Achievement> getCarbonLeaderboard() {
        return service.getCarbonLeaderboard();
    }

    @GetMapping("/calories")
    public List<Achievement> getCaloriesLeaderboard() {
        return service.getCaloriesLeaderboard();
    }
}