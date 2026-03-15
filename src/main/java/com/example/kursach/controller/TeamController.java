package com.example.kursach.controller;

import com.example.kursach.entity.Team;
import com.example.kursach.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/kursach/teams")
@RequiredArgsConstructor
@Slf4j
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<Team>> getTeams(Pageable pageable) throws IOException {
        log.info("Запрос на получение списка команд");
        List<Team> teams = teamService.getTeams(pageable);
        log.info("Ответ: {}", teams);
        return ResponseEntity.ok(teams);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<String> getTeamInfo(@PathVariable Long id) {
        log.info("Запрос на получение информации о команде");
        String team = teamService.getTeamInfo(id);
        log.info("Ответ: {}", team);
        return ResponseEntity.ok(team);
    }
}