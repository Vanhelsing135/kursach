package com.example.kursach.controller;

import com.example.kursach.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kursach/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public Map<String, Object> getFavorites(Authentication authentication) {
        String username = authentication.getName();
        return userService.getFavorites(username);
    }

    @PostMapping("/team")
    public void addFavoriteTeam(Authentication authentication, @RequestBody Map<String, Integer> body) {
        String username = authentication.getName();
        Integer teamId = body.get("teamId");
        userService.addFavoriteTeam(username, teamId);
    }

    @DeleteMapping("/team/{teamId}")
    public void removeFavoriteTeam(Authentication authentication, @PathVariable Integer teamId) {
        String username = authentication.getName();
        userService.removeFavoriteTeam(username, teamId);
    }

    @PostMapping("/competition")
    public void addFavoriteCompetition(Authentication authentication, @RequestBody Map<String, Integer> body) {
        String username = authentication.getName();
        Integer competitionId = body.get("competitionId");
        userService.addFavoriteCompetition(username, competitionId);
    }

    @DeleteMapping("/competition/{competitionId}")
    public void removeFavoriteCompetition(Authentication authentication, @PathVariable Integer competitionId) {
        String username = authentication.getName();
        userService.removeFavoriteCompetition(username, competitionId);
    }

    @GetMapping("/favorite-teams")
    public List<Integer> getFavoriteTeamIds(Authentication authentication) {
        String username = authentication.getName();
        var user = userService.getUserByUsername(username);
        return user.getFavTeams();
    }

    @GetMapping("/favorite-competitions")
    public List<Integer> getFavoriteCompetitionsIds(Authentication authentication) {
        String username = authentication.getName();
        var user = userService.getUserByUsername(username);
        return user.getFavoriteCompetitions();
    }
}