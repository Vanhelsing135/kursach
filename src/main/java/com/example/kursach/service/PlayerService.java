package com.example.kursach.service;

import com.example.kursach.dto.CompetitionsApiResponse;
import com.example.kursach.dto.PlayerDto;
import com.example.kursach.entity.Player;
import com.example.kursach.repository.PlayerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class PlayerService {
    @Value("${external.api.url}")
    private String apiUrl;

    @Value("${external.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate;
    private final PlayerRepository playerRepository;

    public Player getPlayerById(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/persons/" + id)
                .toUriString();

        RequestEntity<Void> requestEntity = RequestEntity.get(url).headers(headers).build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        return parsePlayerFromRepsonse(responseEntity.getBody());
    }

    private Player parsePlayerFromRepsonse(String response) {

        PlayerDto playerDto = new PlayerDto();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            playerDto = objectMapper.readValue(response, PlayerDto.class);
        } catch (Exception e) {
            System.err.println("Error while parsing json");
        }

        return playerRepository.save(new Player(playerDto.getId(), playerDto.getCurrentTeam().getId(), playerDto.getName(),playerDto.getFirstName(), playerDto.getLastName(),playerDto.getDateOfBirth(),playerDto.getNationality(),playerDto.getPosition(),playerDto.getShirtNumber(),playerDto.getLastUpdated()));
    }
}
