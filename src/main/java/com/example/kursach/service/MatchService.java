package com.example.kursach.service;

import com.example.kursach.dto.MatchResponseDto;
import com.example.kursach.entity.*;
import com.example.kursach.entity.MatchMyResponseDto;
import com.example.kursach.repository.CompetitionRepository;
import com.example.kursach.repository.MatchRepository;
import com.example.kursach.repository.SeasonRepository;
import com.example.kursach.repository.TeamRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final SeasonRepository seasonRepository;
    private final CompetitionRepository competitionRepository;
    private final TeamRepository teamRepository;
    private final RestTemplate restTemplate;

    @Value("${external.api.url}")
    private String apiUrl;

    @Value("${external.api.key}")
    private String apiKey;

    public List<MatchMyResponseDto> getMatches() throws JsonProcessingException {
//        if (!matchRepository.findAll().isEmpty()) {
//            return convertToMyDto(matchRepository.findAll());
//        }

        System.err.println("Have data");
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/matches")
                .toUriString();

        RequestEntity<Void> requestEntity = RequestEntity.get(url).headers(headers).build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        MatchResponseDto matchesFromResponse = parseMatchesFromResponse(responseEntity.getBody());

        List<Match> newMatches = new ArrayList<>();
        for (MatchResponseDto.MatchDto m : matchesFromResponse.getMatches()) {
            // Check if home and away teams already exist, if not, create them
            Team homeTeam = teamRepository.findById(m.getHomeTeam().getId())
                    .orElseGet(() -> new Team(m.getHomeTeam().getId(), m.getHomeTeam().getName(), m.getHomeTeam().getShortName(), m.getHomeTeam().getTla(), null, null, null, null, null, null));
            Team awayTeam = teamRepository.findById(m.getAwayTeam().getId())
                    .orElseGet(() -> new Team(m.getAwayTeam().getId(), m.getAwayTeam().getName(), m.getAwayTeam().getShortName(), m.getAwayTeam().getTla(), null, null, null, null, null, null));

            // Save or update teams if necessary
            teamRepository.save(homeTeam);
            teamRepository.save(awayTeam);

            // Determine the winner of the match
            Team winner;
            if (m.getScore().getFullTime().getAway() > m.getScore().getFullTime().getHome()) {
                winner = awayTeam;
            } else if (m.getScore().getFullTime().getAway() < m.getScore().getFullTime().getHome()) {
                winner = homeTeam;
            } else {
                winner = null;
            }

            // Check if the competition already exists, if not, create it
            Competition competition = competitionRepository.findById(m.getCompetition().getId())
                    .orElseGet(() -> new Competition(m.getCompetition().getId(), m.getCompetition().getName(), m.getCompetition().getCode(), m.getCompetition().getType(),
                            m.getCompetition().getEmblem(), m.getArea().getName(), null, m.getSeason().getId(), LocalDateTime.now(), null));

            // Save or update competition if necessary
            competitionRepository.save(competition);

            // Check if the season already exists, if not, create it
            Season season = seasonRepository.findById(m.getSeason().getId())
                    .orElseGet(() -> new Season(m.getSeason().getId(), competition, LocalDate.parse(m.getSeason().getStartDate()), LocalDate.parse(m.getSeason().getEndDate()), winner));

            // Save or update season if necessary
            seasonRepository.save(season);

            // Check if the match already exists, if not, create it
            Match match = matchRepository.findById(m.getId())
                    .orElseGet(() -> new Match(m.getId(),
                            OffsetDateTime.parse(m.getUtcDate()).toLocalDateTime(), m.getStatus(), null, null,
                            m.getStage(), m.getGroup(), OffsetDateTime.parse(m.getLastUpdated()).toLocalDateTime(), homeTeam, awayTeam,
                            m.getScore().getFullTime().getHome(), m.getScore().getFullTime().getAway(), winner, competition, season));

            // Update match fields if necessary
            match.setStatus(m.getStatus());
            match.setStage(m.getStage());
            match.setGroup(m.getGroup());
            match.setLastUpdated(OffsetDateTime.parse(m.getLastUpdated()).toLocalDateTime());

            // Add the match to the list to be saved
            newMatches.add(match);
        }

        // Save all matches (this will update existing ones if they were found by ID)
        matchRepository.saveAll(newMatches);
        return convertToMyDto(newMatches);
    }

    private MatchResponseDto parseMatchesFromResponse(String response) throws JsonProcessingException {
        MatchResponseDto matchResponseDtos = new MatchResponseDto();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            matchResponseDtos = objectMapper.readValue(response, MatchResponseDto.class);
        } catch (Exception e) {
            System.err.println("Error while parsing JSON" + e.getMessage());
        }

        return matchResponseDtos;
    }

    private List<MatchMyResponseDto> convertToMyDto(List<Match> matches){
        List<MatchMyResponseDto> responseDtos = new ArrayList<>();
        for(Match m : matches){
            Long winnerId = 0L;
            if(m.getWinner() != null){
                winnerId = m.getWinner().getId();
            }
            responseDtos.add(new MatchMyResponseDto(m.getId(),m.getUtcDate(),m.getStatus(),m.getVenue(),m.getMatchday(),m.getStage(),m.getGroup(),m.getLastUpdated(),m.getHomeTeam().getId(),m.getAwayTeam().getId(), m.getHomeScore(),m.getAwayScore(), winnerId, m.getCompetition().getName(),m.getSeason().getId()));
        }
        return responseDtos;
    }
}
