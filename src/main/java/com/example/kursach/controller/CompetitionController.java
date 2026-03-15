package com.example.kursach.controller;

import com.example.kursach.dto.TableDto;
import com.example.kursach.dto.TeamApiResponse;
import com.example.kursach.entity.Competition;
import com.example.kursach.repository.CompetitionRepository;
import com.example.kursach.repository.TeamRepository;
import com.example.kursach.service.CompetitionService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
@RequestMapping("/kursach/competitions")
@RequiredArgsConstructor
@Slf4j
public class CompetitionController {

    private final CompetitionService competitionService;
    private final TeamRepository teamRepository;
    private final CompetitionRepository competitionRepository;

    @GetMapping
    public ResponseEntity<List<Competition>> getCompetitions(Pageable pageable) throws IOException {
        log.info("Запрос на получение списка соревнований");
        List<Competition> competitions = competitionService.getCompetitions(pageable);
        log.info("Ответ: {}", competitions);
        return ResponseEntity.ok(competitions);
    }

    @GetMapping("/{competitionId}/teams")
    public ResponseEntity<List<TeamApiResponse.TeamDto>> getTeams(@PathVariable Long competitionId) throws JsonProcessingException {
        log.info("Запрос на получение команд в турнире");
        List<TeamApiResponse.TeamDto> teams = competitionService.getTeamsByCompetition(competitionId);
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{competitionId}/standings")
    public ResponseEntity<List<TableDto>> getStandings(@PathVariable Long competitionId) {
        log.info("Запроса на получение турнирной таблицы по id: {}", competitionId);
        List<TableDto> standings = competitionService.getStandings(competitionId);
        log.info("Ответ на получени етурнирной таблицы по id: {}, ответ: {}", competitionId, standings);
        return ResponseEntity.ok(standings);
    }


}
