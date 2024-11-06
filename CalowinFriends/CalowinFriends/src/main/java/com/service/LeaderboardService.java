package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.models.Achievement;
import com.repository.AchievementRepository;

@Service
public class LeaderboardService {

    @Autowired
    private AchievementRepository repository;

    // Leaderboard based on total carbon saved
    public List<Achievement> getCarbonLeaderboard() {
        return repository.findAll().stream()
                .sorted((a, b) -> Integer.compare(b.getTotalCarbonSaved(), a.getTotalCarbonSaved()))
                .toList();
    }

    // Leaderboard based on total calories burnt
    public List<Achievement> getCaloriesLeaderboard() {
        return repository.findAll().stream()
                .sorted((a, b) -> Integer.compare(b.getTotalCalorieBurnt(), a.getTotalCalorieBurnt()))
                .toList();
    }
}