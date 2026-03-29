package com.example.kursach.controller;

import com.example.kursach.dto.PlayerResponseDTO;
import com.example.kursach.dto.TeamApiResponse;
import com.example.kursach.entity.Competition;
import com.example.kursach.entity.Player;
import com.example.kursach.entity.Team;
import com.example.kursach.service.CompetitionService;
import com.example.kursach.service.PlayerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/kursach/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/v2/{playerId}")
    public ResponseEntity<PlayerResponseDTO> getTeams(@PathVariable Long playerId) {
        return ResponseEntity.ok(playerService.getPlayerResponseDTOById(playerId));
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<String> getPlayerInfo(@PathVariable Long playerId) {
        log.info("Запрос на получение информации об игроке по id: {}", playerId);
        return ResponseEntity.ok(playerService.getPlayerInfo(playerId));
    }
}