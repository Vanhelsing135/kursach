package com.example.kursach.service;

import com.example.kursach.dto.CompetitionsApiResponse;
import com.example.kursach.dto.StandingsDto;
import com.example.kursach.dto.TableDto;
import com.example.kursach.dto.TeamApiResponse;
import com.example.kursach.entity.*;
import com.example.kursach.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetitionService {

    private final PlayerRepository playerRepository;
    @Value("${external.api.url}")
    private String apiUrl;

    @Value("${external.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate;
    private final CompetitionRepository competitionRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;
    private final StandingsRepository standingsRepository;

    @Transactional
    public List<Competition> getCompetitions() {
        List<Competition> competitions = competitionRepository.findByLastUpdatedBefore(LocalDateTime.now().minusDays(1L));

        if (!competitions.isEmpty()) {
            System.out.println("HAVE INFO");
            return competitions;
        }
        System.out.println("NO INFO");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/competitions")
                .toUriString();

        RequestEntity<Void> requestEntity = RequestEntity.get(url).headers(headers).build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        CompetitionsApiResponse competitionsApiResponse = parseCompetitionsFromResponse(responseEntity.getBody());

        List<Competition> newCompetitions = new ArrayList<>();
        for (CompetitionsApiResponse.Competition competitionDto : competitionsApiResponse.getCompetitions()) {
            Competition competition = new Competition();
            competition.setId(competitionDto.getId());
            competition.setName(competitionDto.getName());
            competition.setCode(competitionDto.getCode());
            competition.setType(competitionDto.getType());
            competition.setEmblemUrl(competitionDto.getEmblem());
            competition.setCountry(competitionDto.getArea().getName());
            competition.setPlan(competitionDto.getPlan());
            competition.setLastUpdated(LocalDateTime.now());

            if (competitionDto.getCurrentSeason() != null) {
                Season currentSeason = new Season();
                currentSeason.setId(competitionDto.getCurrentSeason().getId());
                currentSeason.setStartDate(LocalDate.parse(competitionDto.getCurrentSeason().getStartDate()));
                currentSeason.setEndDate(LocalDate.parse(competitionDto.getCurrentSeason().getEndDate()));
                currentSeason.setCompetition(competition);
                competition.setSeasonId(currentSeason.getId());
                competitionRepository.save(competition);
                seasonRepository.save(currentSeason);
//                saveSeason(currentSeason);
//                seasonRepository.save(currentSeason);
//                competition.setCurrentSeason(currentSeason);
//                competitionRepository.save(competition);

            }

//            newCompetitions.add(competition);
        }


//    for (Competition competition : newCompetitions) {
//        competition = entityManager.merge(competition); // Слияние сущности с контекстом
//        competitionRepository.save(competition);
//    }


//        competitionRepository.saveAll(newCompetitions);

        return newCompetitions;
    }

    //    public void save(Competition c){
//    competitionRepository.save(c)
//    }
    public CompetitionsApiResponse parseCompetitionsFromResponse(String response) {
        CompetitionsApiResponse competitionsApiResponse = new CompetitionsApiResponse();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            competitionsApiResponse = objectMapper.readValue(response, CompetitionsApiResponse.class);
        } catch (Exception e) {
            System.err.println("Error while parsing json");
        }

        return competitionsApiResponse;
    }


    @Transactional
    public List<TeamApiResponse.TeamDto> getTeamsByCompetition(Long competitionId) throws JsonProcessingException {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition not found with ID: " + competitionId));

        //надо изменить competition, чтобы в нем был список команд
        List<Team> teams = competitionRepository.getById(competitionId).getTeams();
//        if (!teams.isEmpty()) {
//            return null;
//        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/competitions/" + competitionId + "/teams")
                .toUriString();

        RequestEntity<Void> requestEntity = RequestEntity.get(url).headers(headers).build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        List<TeamApiResponse.TeamDto> teamDtos = parseTeamsFromResponse(responseEntity.getBody()).getTeams();

        for (TeamApiResponse.TeamDto t : teamDtos) {
            List<TeamApiResponse.TeamDto.SquadMemberDto> playersDto = t.getSquad();
            List<Player> newPlayers = new ArrayList<>();
            for (TeamApiResponse.TeamDto.SquadMemberDto p : playersDto) {
                Player newPlayer = new Player();
                newPlayer.setDateOfBirth(p.getDateOfBirth());
                newPlayer.setId(p.getId());
                newPlayer.setTeamId(t.getId());
                newPlayer.setName(p.getName());
                newPlayer.setNationality(p.getNationality());
                newPlayer.setLastUpdated(LocalDateTime.now().toString());
                newPlayer.setPosition(p.getPosition());
                newPlayers.add(newPlayer);
            }

            List<Competition> competitions = new ArrayList<>();
            for (TeamApiResponse.CompetitionDto c : t.getRunningCompetitions()) {
                competitions.add(new Competition(c.getId(), c.getName(), c.getCode(), c.getType(), c.getEmblem(), t.getArea().getName(), null, null, null, null));
            }
            competitionRepository.saveAll(competitions);
            if (!teamRepository.existsById(t.getId())) {
//                teams.add(new Team(t.getId(), t.getName(), t.getShortName(), t.getTla(), t.getAddress(), t.getFounded(), t.getClubColors(), t.getVenue(), competitions, newPlayers));
                teamRepository.save(new Team(t.getId(), t.getName(), t.getShortName(), t.getTla(), t.getAddress(), t.getFounded(), t.getClubColors(), t.getVenue(), competitions, newPlayers));

            }
            playerRepository.saveAll(newPlayers);
        }
        teamRepository.saveAll(teams);
        return teamDtos;
    }

    private TeamApiResponse parseTeamsFromResponse(String response) throws JsonProcessingException {
        TeamApiResponse teamApiResponse = new TeamApiResponse();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            teamApiResponse = objectMapper.readValue(response, TeamApiResponse.class);
        } catch (Exception e) {
            System.err.println("Error while parsing json");
        }

//        ObjectMapper objectMapper = new ObjectMapper();
//        teamApiResponse = objectMapper.readValue(response, TeamApiResponse.class);
        return teamApiResponse;
    }

//    @Transactional
//    protected void saveSeason(Season season){
//        seasonRepository.save(season);
//    }


    public List<TableDto> getStandings(Long competitionId) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition not found with ID: " + competitionId));

        List<Standings> standings = standingsRepository.findByCompetition(competition);
        if (!standings.isEmpty()) {
            List<TableDto> table = new ArrayList<>();
            for (Standings s : standings) {
                table.add(new TableDto(s.getTeam().getName(), s.getPosition(), s.getPoints(), s.getPlayedGames(), s.getLost(), s.getWon(), s.getDraw(), s.getGoalsAgainst(), s.getGoalsFor(), s.getGoalDifference()));
            }

            return table.stream().sorted((a, b) -> Integer.compare(a.getPosition(), b.getPosition())).toList();

        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/competitions/" + competitionId + "/standings")
                .toUriString();

        RequestEntity<Void> requestEntity = RequestEntity.get(url).headers(headers).build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        StandingsDto standingsDto = parseStandingsFromResponse(responseEntity.getBody());
        if (!seasonRepository.existsById(standingsDto.getSeason().getId())) {
            Season season = new Season();
            season.setId(standingsDto.getSeason().getId());
            season.setCompetition(competition);
            season.setEndDate(LocalDate.parse(standingsDto.getSeason().getEndDate()));
            season.setStartDate(LocalDate.parse(standingsDto.getSeason().getStartDate()));
            seasonRepository.save(season);
        }
        List<StandingsDto.Standings.Table> stands = standingsDto.getStandings().get(0).getTable();
        List<Standings> newStandings = new ArrayList<>();
        for (StandingsDto.Standings.Table s : stands) {
            Standings standings1 = new Standings();
            standings1.setCompetition(competition);
            standings1.setForm(s.getForm());
            standings1.setDraw(s.getDraw());
            standings1.setLost(s.getLost());
            standings1.setPosition(s.getPosition());
            standings1.setType(standingsDto.getStandings().get(0).getType());
            standings1.setSeason(seasonRepository.findById(standingsDto.getSeason().getId()).orElseThrow());
            standings1.setPoints(s.getPoints());
            standings1.setGoalsAgainst(s.getGoalsAgainst());
            standings1.setGoalsFor(s.getGoalsFor());
            standings1.setPlayedGames(s.getPlayedGames());
            standings1.setStage(standingsDto.getStandings().get(0).getStage());
            standings1.setWon(s.getWon());
            StandingsDto.Standings.Table.TeamDto teamDto = s.getTeam();
            if (!teamRepository.existsById(teamDto.getId())) {
                Team team = new Team();
                team.setShortName(teamDto.getShortName());
                team.setName(teamDto.getName());
                team.setId(teamDto.getId());
                team.setTla(teamDto.getTla());
                teamRepository.save(team);
                standings1.setTeam(team);
            }
            standings1.setTeam(teamRepository.findById(teamDto.getId()).orElseThrow());
            newStandings.add(standings1);

        }

        standingsRepository.saveAll(newStandings);

        List<TableDto> table = new ArrayList<>();
        for (Standings s : newStandings) {
            table.add(new TableDto(s.getTeam().getName(), s.getPosition(), s.getPoints(), s.getPlayedGames(), s.getLost(), s.getWon(), s.getDraw(), s.getGoalsAgainst(), s.getGoalsFor(), s.getGoalDifference()));
        }

        return table.stream().sorted((a, b) -> Integer.compare(a.getPosition(), b.getPosition())).toList();
    }


    private StandingsDto parseStandingsFromResponse(String response) {
        StandingsDto standingsDto = new StandingsDto();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            standingsDto = objectMapper.readValue(response, StandingsDto.class);
        } catch (Exception e) {
            System.err.println("Error while parsing" + e.getMessage());
        }
        return standingsDto;
    }
}
