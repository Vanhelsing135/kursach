package com.example.kursach.service;

import com.example.kursach.dto.CompetitionsApiResponse;
import com.example.kursach.dto.PlayerDto;
import com.example.kursach.dto.PlayerResponseDTO;
import com.example.kursach.entity.Competition;
import com.example.kursach.entity.Player;
import com.example.kursach.entity.Team;
import com.example.kursach.repository.CompetitionRepository;
import com.example.kursach.repository.PlayerRepository;
import com.example.kursach.repository.TeamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    @Value("${external.api.url}")
    private String apiUrl;

    @Value("${external.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final CompetitionRepository competitionRepository;

    public Player getPlayerById(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/persons/" + id)
                .toUriString();

        RequestEntity<Void> requestEntity = RequestEntity.get(url).headers(headers).build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        System.out.println("был");
        return parsePlayerFromRepsonse(responseEntity.getBody());
    }

    public PlayerResponseDTO getPlayerResponseDTOById(Long id){
        return convertToResponseDTO(getPlayerById(id));
    }

    private Player parsePlayerFromRepsonse(String response) {

        PlayerDto playerDto = new PlayerDto();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            playerDto = objectMapper.readValue(response, PlayerDto.class);
        } catch (Exception e) {
            System.err.println("Error while parsing json");
        }

        if (teamRepository.existsById(playerDto.getCurrentTeam().getId())) {
            return savePlayer(playerDto);
//            return playerRepository.save(new Player(playerDto.getId(), playerDto.getCurrentTeam().getId(), playerDto.getName(), playerDto.getFirstName(), playerDto.getLastName(), playerDto.getDateOfBirth(), playerDto.getNationality(), playerDto.getPosition(), playerDto.getShirtNumber(), playerDto.getLastUpdated()));
        } else {
            PlayerDto.CurTeamDto teamDto = playerDto.getCurrentTeam();
            Team team = new Team();
            team.setId(teamDto.getId());
            team.setName(teamDto.getName());
            team.setAddress(teamDto.getAddress());
            team.setFounded(teamDto.getFounded());
            team.setTla(teamDto.getTla());
            team.setVenue(teamDto.getVenue());
            team.setClubColors(teamDto.getClubColors());
            team.setShortName(teamDto.getShortName());
            List<Player> squad = new ArrayList<>();
            squad.add(savePlayer(playerDto));
//            squad.add(new Player(playerDto.getId(), playerDto.getCurrentTeam().getId(), playerDto.getName(), playerDto.getFirstName(), playerDto.getLastName(), playerDto.getDateOfBirth(), playerDto.getNationality(), playerDto.getPosition(), playerDto.getShirtNumber(), playerDto.getLastUpdated()));
            team.setSquad(squad);
            List<Competition> competitions = new ArrayList<>();
            List<PlayerDto.CurTeamDto.CompetitionDto> competitionDtos = playerDto.getCurrentTeam().getRunningCompetitions();
            for (PlayerDto.CurTeamDto.CompetitionDto c : competitionDtos) {
                if (competitionRepository.existsById(c.getId())) {
                    competitions.add(competitionRepository.findById(c.getId()).orElseThrow());
                } else {
                    List<Team> teams = new ArrayList<>();
                    teams.add(team);
                    competitions.add(new Competition(c.getId(), c.getName(), c.getCode(), c.getType(), c.getEmblem(), playerDto.getCurrentTeam().getArea().getName(), null, null, null, teams));
                }
            }
            teamRepository.save(team);
            competitionRepository.saveAll(competitions);
            return savePlayer(playerDto);
//            return playerRepository.save(new Player(playerDto.getId(), playerDto.getCurrentTeam().getId(), playerDto.getName(), playerDto.getFirstName(), playerDto.getLastName(), playerDto.getDateOfBirth(), playerDto.getNationality(), playerDto.getPosition(), playerDto.getShirtNumber(), playerDto.getLastUpdated()));
        }
    }

    public Player savePlayer(PlayerDto playerDto) {
        Long teamId = playerDto.getCurrentTeam().getId();

        // Проверка наличия команды
        Team team = teamRepository.findById(teamId).orElseGet(() -> {
            // Если команды нет — создаём новую
            Team newTeam = new Team();
            newTeam.setId(teamId);
            newTeam.setName(playerDto.getCurrentTeam().getName());
            newTeam.setShortName(playerDto.getCurrentTeam().getShortName());
            newTeam.setTla(playerDto.getCurrentTeam().getTla());
            newTeam.setAddress(playerDto.getCurrentTeam().getAddress());
            newTeam.setFounded(playerDto.getCurrentTeam().getFounded());
            newTeam.setClubColors(playerDto.getCurrentTeam().getClubColors());
            newTeam.setVenue(playerDto.getCurrentTeam().getVenue());
            return teamRepository.save(newTeam);
        });

        // Создаём игрока
        Player player = new Player();
        player.setId(playerDto.getId());
        player.setTeam(team);
        player.setName(playerDto.getName());
        player.setFirstName(playerDto.getFirstName());
        player.setLastName(playerDto.getLastName());
        player.setDateOfBirth(playerDto.getDateOfBirth());
        player.setNationality(playerDto.getNationality());
        player.setPosition(playerDto.getPosition());
        player.setShirtNumber(playerDto.getShirtNumber());
        player.setLastUpdated(playerDto.getLastUpdated());

        return playerRepository.save(player);
    }

    public PlayerResponseDTO convertToResponseDTO(Player player) {
        PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO();
        playerResponseDTO.setId(player.getId());
        playerResponseDTO.setName(player.getName());
        playerResponseDTO.setLastName(player.getLastName());
        playerResponseDTO.setNationality(player.getNationality());
        playerResponseDTO.setPosition(player.getPosition());
        playerResponseDTO.setShirtNumber(player.getShirtNumber());
        playerResponseDTO.setFirstName(player.getFirstName());
        playerResponseDTO.setLastUpdated(player.getLastUpdated());
        playerResponseDTO.setDateOfBirth(player.getDateOfBirth());
        playerResponseDTO.setCurrentTeam(player.getTeam().getName());
        return playerResponseDTO;
    }

}
