package com.example.kursach.service;

import com.example.kursach.entity.Team;
import com.example.kursach.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    @Value("${external.api.url}")
    private String apiUrl;

    @Value("${external.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate;

    @Transactional
    public List<Team> getTeams(Pageable pageable) {
        return teamRepository.findAll(pageable).stream().toList();
    }

    @Transactional
    public String getTeamInfo(Long id) {
        log.info("Отправка запроса на {}", apiUrl + "/teams/" + id);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/teams/" + id)
                .toUriString();

        RequestEntity<Void> requestEntity = RequestEntity.get(url).headers(headers).build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        return responseEntity.getBody();
//        Team t = teamRepository.findById(id).get();
//        TeamDto result = new TeamDto();
//        result.setAddress(t.getAddress());
//        result.setName(t.getName());
//        result.setFounded(t.getFounded());
//        result.setCrest(t.getCrest());
//        result.setTla(t.getTla());
//        result.setClubColors(t.getClubColors());
//        result.setVenue(t.getVenue());
//        result.setShortName(t.getShortName());
//
//        List<Pair<String, Pair<String, Integer>>> players = new ArrayList<>();
//        for (Player p : t.getSquad()) {
//            players.add(Pair.of(p.getName(), Pair.of(p.getPosition(), p.getShirtNumber())));
//        }
//        result.setPlayers(players);
//
//        List<Pair<String, String>> competitions = new ArrayList<>();
//        for (Competition c : t.getRunningCompetitions()) {
//            competitions.add(Pair.of(c.getName(), c.getEmblemUrl()));
//        }
//        result.setRunningCompetitions(competitions);
//        return result;
    }

    public String getTeamMatches(Long id, String status){
        log.info("Отправка запроса на {}", apiUrl + "/teams/" + id + "/matches?status=" + status);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/teams/" + id + "/matches?status=" + status + "&dateFrom=" + LocalDate.now().minusWeeks(3) + "&dateTo=" + LocalDate.now())
                .toUriString();

        RequestEntity<Void> requestEntity = RequestEntity.get(url).headers(headers).build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        return responseEntity.getBody();
    }
}