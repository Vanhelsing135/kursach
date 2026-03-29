package com.example.kursach.service;

import com.example.kursach.entity.User;
import com.example.kursach.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Value("${external.api.url}")
    private String apiUrl;

    @Value("${external.api.key}")
    private String apiKey;

    public List<User> allUsers(){
        return new ArrayList<>(userRepository.findAll());
    }


    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        return userRepository.save(user);
    }

    public UserDetailsService userDetailsService() {
        return this::getUserByUsername;
    }


    public void deleteUser(String username) {
        var user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        userRepository.delete(user);
    }


    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByUsername(username);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public Map<String, Object> getFavorites(String username) {
        Map<String, Object> result = new HashMap<>();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Map<String, Object>> favoriteTeams = new ArrayList<>();
        List<Map<String, Object>> favoriteCompetitions = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);

        for (Integer teamId : user.getFavTeams()) {
            try {
                String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/teams/" + teamId)
                        .toUriString();
                RequestEntity<Void> request = RequestEntity.get(url).headers(headers).build();
                ResponseEntity<Map> response = restTemplate.exchange(request, Map.class);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    Map<String, Object> body = response.getBody();

                    Map<String, Object> teamData = new HashMap<>();
                    teamData.put("id", body.get("id"));
                    teamData.put("name", body.get("name"));
                    teamData.put("country", ((Map) body.get("area")).get("name"));
                    teamData.put("crest", body.get("crest"));

                    favoriteTeams.add(teamData);
                }
            } catch (Exception e) {
                log.error("Ошибка при запросе команды {}: {}", teamId, e.getMessage());
            }
        }

        for (Integer competitionId : user.getFavoriteCompetitions()) {
            try {
                String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/competitions/" + competitionId)
                        .toUriString();
                RequestEntity<Void> request = RequestEntity.get(url).headers(headers).build();
                ResponseEntity<Map> response = restTemplate.exchange(request, Map.class);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    Map<String, Object> body = response.getBody();

                    Map<String, Object> competitionData = new HashMap<>();
                    competitionData.put("id", body.get("id"));
                    competitionData.put("name", body.get("name"));
                    competitionData.put("country", ((Map) body.get("area")).get("name"));
                    competitionData.put("emblemUrl", body.get("emblem"));

                    favoriteCompetitions.add(competitionData);
                }
            } catch (Exception e) {
                log.error("Ошибка при запросе соревнования {}: {}", competitionId, e.getMessage());
            }
        }

        result.put("teams", favoriteTeams);
        result.put("competitions", favoriteCompetitions);

        return result;
    }

    @Transactional
    public void addFavoriteTeam(String username, Integer teamId) {
        User user = getUserByUsername(username);
        if (!user.getFavTeams().contains(teamId)) {
            user.getFavTeams().add(teamId);
            userRepository.save(user);
        }
    }

    @Transactional
    public void removeFavoriteTeam(String username, Integer teamId) {
        User user = getUserByUsername(username);
        user.getFavTeams().remove(teamId);
        userRepository.save(user);
    }

    @Transactional
    public void addFavoriteCompetition(String username, Integer competitionId) {
        User user = getUserByUsername(username);
        if (!user.getFavoriteCompetitions().contains(competitionId)) {
            user.getFavoriteCompetitions().add(competitionId);
            userRepository.save(user);
        }
    }

    @Transactional
    public void removeFavoriteCompetition(String username, Integer competitionId) {
        User user = getUserByUsername(username);
        user.getFavoriteCompetitions().remove(competitionId);
        userRepository.save(user);
    }
}