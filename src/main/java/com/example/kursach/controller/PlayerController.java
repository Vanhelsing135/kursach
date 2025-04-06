package com.example.kursach.controller;

import com.example.kursach.dto.TeamApiResponse;
import com.example.kursach.entity.Competition;
import com.example.kursach.entity.Player;
import com.example.kursach.entity.Team;
import com.example.kursach.service.CompetitionService;
import com.example.kursach.service.PlayerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kursach/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/{playerId}")
    public ResponseEntity<Player> getTeams(@PathVariable Long playerId) throws JsonProcessingException {
        for (int i = 1; i < 20000; i++) {
            try {
                playerService.getPlayerById(playerId + i);
            } catch (Exception e) {
                System.err.println(playerId + i);
            }
        }
        return ResponseEntity.ok(null);
    }


}
