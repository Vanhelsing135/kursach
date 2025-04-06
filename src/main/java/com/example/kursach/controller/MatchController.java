package com.example.kursach.controller;

import com.example.kursach.dto.MatchResponseDto;
import com.example.kursach.dto.TeamApiResponse;
import com.example.kursach.entity.Competition;
import com.example.kursach.entity.Match;
import com.example.kursach.entity.MatchMyResponseDto;
import com.example.kursach.entity.Team;
import com.example.kursach.service.CompetitionService;
import com.example.kursach.service.MatchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kursach/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchMyResponseDto>> getCompetitions() throws JsonProcessingException {
        List<MatchMyResponseDto> matches = matchService.getMatches();
        return ResponseEntity.ok(matches);
    }

//    @GetMapping("/{competitionId}/teams")
//    public ResponseEntity<List<TeamApiResponse.TeamDto>> getTeams(@PathVariable Long competitionId) throws JsonProcessingException {
//        List<TeamApiResponse.TeamDto> teams = competitionService.getTeamsByCompetition(competitionId);
//        return ResponseEntity.ok(teams);
//    }


}
