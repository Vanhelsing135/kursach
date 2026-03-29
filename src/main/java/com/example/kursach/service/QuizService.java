package com.example.kursach.service;

import com.example.kursach.dto.QuestionDto;
import com.example.kursach.entity.Match;
import com.example.kursach.entity.Player;
import com.example.kursach.entity.Team;
import com.example.kursach.repository.MatchRepository;
import com.example.kursach.repository.PlayerRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizService {
    @Value("${external.api.url}")
    private String apiUrl;

    @Value("${external.api.key}")
    private String apiKey;
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final RestTemplate restTemplate;

    public List<QuestionDto> generateQuizForMatch(Long matchId) {
        if (true) {
            log.info("Отправка запроса на {}", apiUrl + "/matches/" + matchId + "/head2head");
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Auth-Token", apiKey);

//            String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/matches/" + matchId + "/head2head")
//                    .toUriString();
//
//            RequestEntity<Void> requestEntity = RequestEntity.get(url).headers(headers).build();
//
//            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
//            String json = responseEntity.getBody();
            String json = """
                    {
                        "filters": {
                            "limit": 15,
                            "permission": "TIER_ONE"
                        },
                        "resultSet": {
                            "count": 8,
                            "competitions": "BSA",
                            "first": "2020-09-09",
                            "last": "2024-10-05"
                        },
                        "aggregates": {
                            "numberOfMatches": 15,
                            "totalGoals": 37,
                            "homeTeam": {
                                "id": 1768,
                                "name": "CA Paranaense",
                                "wins": 0,
                                "draws": 3,
                                "losses": 0
                            },
                            "awayTeam": {
                                "id": 1770,
                                "name": "Botafogo FR",
                                "wins": 0,
                                "draws": 3,
                                "losses": 0
                            }
                        },
                        "matches": [
                            {
                                "area": {
                                    "id": 2032,
                                    "name": "Brazil",
                                    "code": "BRA",
                                    "flag": "https://crests.football-data.org/764.svg"
                                },
                                "competition": {
                                    "id": 2013,
                                    "name": "Campeonato Brasileiro Série A",
                                    "code": "BSA",
                                    "type": "LEAGUE",
                                    "emblem": "https://crests.football-data.org/bsa.png"
                                },
                                "season": {
                                    "id": 2257,
                                    "startDate": "2024-04-13",
                                    "endDate": "2024-12-08",
                                    "currentMatchday": 38,
                                    "winner": null
                                },
                                "id": 494033,
                                "utcDate": "2024-10-05T19:30:00Z",
                                "status": "FINISHED",
                                "matchday": 29,
                                "stage": "REGULAR_SEASON",
                                "group": null,
                                "lastUpdated": "2024-12-22T20:20:40Z",
                                "homeTeam": {
                                    "id": 1768,
                                    "name": "CA Paranaense",
                                    "shortName": "Paranaense",
                                    "tla": "CAP",
                                    "crest": "https://crests.football-data.org/1768.png"
                                },
                                "awayTeam": {
                                    "id": 1770,
                                    "name": "Botafogo FR",
                                    "shortName": "Botafogo",
                                    "tla": "BOT",
                                    "crest": "https://crests.football-data.org/1770.png"
                                },
                                "score": {
                                    "winner": "AWAY_TEAM",
                                    "duration": "REGULAR",
                                    "fullTime": {
                                        "home": 0,
                                        "away": 1
                                    },
                                    "halfTime": {
                                        "home": 0,
                                        "away": 1
                                    }
                                },
                                "odds": {
                                    "msg": "Activate Odds-Package in User-Panel to retrieve odds."
                                },
                                "referees": []
                            },
                            {
                                "area": {
                                    "id": 2032,
                                    "name": "Brazil",
                                    "code": "BRA",
                                    "flag": "https://crests.football-data.org/764.svg"
                                },
                                "competition": {
                                    "id": 2013,
                                    "name": "Campeonato Brasileiro Série A",
                                    "code": "BSA",
                                    "type": "LEAGUE",
                                    "emblem": "https://crests.football-data.org/bsa.png"
                                },
                                "season": {
                                    "id": 2257,
                                    "startDate": "2024-04-13",
                                    "endDate": "2024-12-08",
                                    "currentMatchday": 38,
                                    "winner": null
                                },
                                "id": 493844,
                                "utcDate": "2024-06-19T22:00:00Z",
                                "status": "FINISHED",
                                "matchday": 10,
                                "stage": "REGULAR_SEASON",
                                "group": null,
                                "lastUpdated": "2024-12-22T20:20:40Z",
                                "homeTeam": {
                                    "id": 1770,
                                    "name": "Botafogo FR",
                                    "shortName": "Botafogo",
                                    "tla": "BOT",
                                    "crest": "https://crests.football-data.org/1770.png"
                                },
                                "awayTeam": {
                                    "id": 1768,
                                    "name": "CA Paranaense",
                                    "shortName": "Paranaense",
                                    "tla": "CAP",
                                    "crest": "https://crests.football-data.org/1768.png"
                                },
                                "score": {
                                    "winner": "DRAW",
                                    "duration": "REGULAR",
                                    "fullTime": {
                                        "home": 1,
                                        "away": 1
                                    },
                                    "halfTime": {
                                        "home": 0,
                                        "away": 0
                                    }
                                },
                                "odds": {
                                    "msg": "Activate Odds-Package in User-Panel to retrieve odds."
                                },
                                "referees": []
                            },
                            {
                                "area": {
                                    "id": 2032,
                                    "name": "Brazil",
                                    "code": "BRA",
                                    "flag": "https://crests.football-data.org/764.svg"
                                },
                                "competition": {
                                    "id": 2013,
                                    "name": "Campeonato Brasileiro Série A",
                                    "code": "BSA",
                                    "type": "LEAGUE",
                                    "emblem": "https://crests.football-data.org/bsa.png"
                                },
                                "season": {
                                    "id": 1557,
                                    "startDate": "2023-04-15",
                                    "endDate": "2023-12-03",
                                    "currentMatchday": 37,
                                    "winner": null
                                },
                                "id": 432170,
                                "utcDate": "2023-10-22T00:00:00Z",
                                "status": "FINISHED",
                                "matchday": 28,
                                "stage": "REGULAR_SEASON",
                                "group": null,
                                "lastUpdated": "2023-10-23T12:17:41Z",
                                "homeTeam": {
                                    "id": 1770,
                                    "name": "Botafogo FR",
                                    "shortName": "Botafogo",
                                    "tla": "BOT",
                                    "crest": "https://crests.football-data.org/1770.png"
                                },
                                "awayTeam": {
                                    "id": 1768,
                                    "name": "CA Paranaense",
                                    "shortName": "Paranaense",
                                    "tla": "CAP",
                                    "crest": "https://crests.football-data.org/1768.png"
                                },
                                "score": {
                                    "winner": "DRAW",
                                    "duration": "REGULAR",
                                    "fullTime": {
                                        "home": 1,
                                        "away": 1
                                    },
                                    "halfTime": {
                                        "home": 1,
                                        "away": 1
                                    }
                                },
                                "odds": {
                                    "msg": "Activate Odds-Package in User-Panel to retrieve odds."
                                },
                                "referees": []
                            },
                            {
                                "area": {
                                    "id": 2032,
                                    "name": "Brazil",
                                    "code": "BRA",
                                    "flag": "https://crests.football-data.org/764.svg"
                                },
                                "competition": {
                                    "id": 2013,
                                    "name": "Campeonato Brasileiro Série A",
                                    "code": "BSA",
                                    "type": "LEAGUE",
                                    "emblem": "https://crests.football-data.org/bsa.png"
                                },
                                "season": {
                                    "id": 1557,
                                    "startDate": "2023-04-15",
                                    "endDate": "2023-12-03",
                                    "currentMatchday": 37,
                                    "winner": null
                                },
                                "id": 432365,
                                "utcDate": "2023-06-03T21:30:00Z",
                                "status": "FINISHED",
                                "matchday": 9,
                                "stage": "REGULAR_SEASON",
                                "group": null,
                                "lastUpdated": "2023-10-09T15:20:25Z",
                                "homeTeam": {
                                    "id": 1768,
                                    "name": "CA Paranaense",
                                    "shortName": "Paranaense",
                                    "tla": "CAP",
                                    "crest": "https://crests.football-data.org/1768.png"
                                },
                                "awayTeam": {
                                    "id": 1770,
                                    "name": "Botafogo FR",
                                    "shortName": "Botafogo",
                                    "tla": "BOT",
                                    "crest": "https://crests.football-data.org/1770.png"
                                },
                                "score": {
                                    "winner": "HOME_TEAM",
                                    "duration": "REGULAR",
                                    "fullTime": {
                                        "home": 1,
                                        "away": 0
                                    },
                                    "halfTime": {
                                        "home": 1,
                                        "away": 0
                                    }
                                },
                                "odds": {
                                    "msg": "Activate Odds-Package in User-Panel to retrieve odds."
                                },
                                "referees": [
                                    {
                                        "id": 206923,
                                        "name": "Vinicius Gonçalves",
                                        "type": "REFEREE",
                                        "nationality": "Brazil"
                                    }
                                ]
                            },
                            {
                                "area": {
                                    "id": 2032,
                                    "name": "Brazil",
                                    "code": "BRA",
                                    "flag": "https://crests.football-data.org/764.svg"
                                },
                                "competition": {
                                    "id": 2013,
                                    "name": "Campeonato Brasileiro Série A",
                                    "code": "BSA",
                                    "type": "LEAGUE",
                                    "emblem": "https://crests.football-data.org/bsa.png"
                                },
                                "season": {
                                    "id": 1377,
                                    "startDate": "2022-04-10",
                                    "endDate": "2022-11-13",
                                    "currentMatchday": 38,
                                    "winner": null
                                },
                                "id": 390021,
                                "utcDate": "2022-11-13T19:00:00Z",
                                "status": "FINISHED",
                                "matchday": 38,
                                "stage": "REGULAR_SEASON",
                                "group": null,
                                "lastUpdated": "2023-02-18T08:20:12Z",
                                "homeTeam": {
                                    "id": 1768,
                                    "name": "CA Paranaense",
                                    "shortName": "Paranaense",
                                    "tla": "CAP",
                                    "crest": "https://crests.football-data.org/1768.png"
                                },
                                "awayTeam": {
                                    "id": 1770,
                                    "name": "Botafogo FR",
                                    "shortName": "Botafogo",
                                    "tla": "BOT",
                                    "crest": "https://crests.football-data.org/1770.png"
                                },
                                "score": {
                                    "winner": "HOME_TEAM",
                                    "duration": "REGULAR",
                                    "fullTime": {
                                        "home": 3,
                                        "away": 0
                                    },
                                    "halfTime": {
                                        "home": 0,
                                        "away": 0
                                    }
                                },
                                "odds": {
                                    "msg": "Activate Odds-Package in User-Panel to retrieve odds."
                                },
                                "referees": []
                            },
                            {
                                "area": {
                                    "id": 2032,
                                    "name": "Brazil",
                                    "code": "BRA",
                                    "flag": "https://crests.football-data.org/764.svg"
                                },
                                "competition": {
                                    "id": 2013,
                                    "name": "Campeonato Brasileiro Série A",
                                    "code": "BSA",
                                    "type": "LEAGUE",
                                    "emblem": "https://crests.football-data.org/bsa.png"
                                },
                                "season": {
                                    "id": 1377,
                                    "startDate": "2022-04-10",
                                    "endDate": "2022-11-13",
                                    "currentMatchday": 38,
                                    "winner": null
                                },
                                "id": 390215,
                                "utcDate": "2022-07-24T00:00:00Z",
                                "status": "FINISHED",
                                "matchday": 19,
                                "stage": "REGULAR_SEASON",
                                "group": null,
                                "lastUpdated": "2023-02-18T08:20:12Z",
                                "homeTeam": {
                                    "id": 1770,
                                    "name": "Botafogo FR",
                                    "shortName": "Botafogo",
                                    "tla": "BOT",
                                    "crest": "https://crests.football-data.org/1770.png"
                                },
                                "awayTeam": {
                                    "id": 1768,
                                    "name": "CA Paranaense",
                                    "shortName": "Paranaense",
                                    "tla": "CAP",
                                    "crest": "https://crests.football-data.org/1768.png"
                                },
                                "score": {
                                    "winner": "HOME_TEAM",
                                    "duration": "REGULAR",
                                    "fullTime": {
                                        "home": 2,
                                        "away": 0
                                    },
                                    "halfTime": {
                                        "home": 1,
                                        "away": 0
                                    }
                                },
                                "odds": {
                                    "msg": "Activate Odds-Package in User-Panel to retrieve odds."
                                },
                                "referees": []
                            },
                            {
                                "area": {
                                    "id": 2032,
                                    "name": "Brazil",
                                    "code": "BRA",
                                    "flag": "https://crests.football-data.org/764.svg"
                                },
                                "competition": {
                                    "id": 2013,
                                    "name": "Campeonato Brasileiro Série A",
                                    "code": "BSA",
                                    "type": "LEAGUE",
                                    "emblem": "https://crests.football-data.org/bsa.png"
                                },
                                "season": {
                                    "id": 589,
                                    "startDate": "2020-08-09",
                                    "endDate": "2021-02-24",
                                    "currentMatchday": 38,
                                    "winner": {
                                        "id": 1783,
                                        "name": "CR Flamengo",
                                        "shortName": "Flamengo",
                                        "tla": "FLA",
                                        "crest": "https://crests.football-data.org/1783.png",
                                        "address": "null Rio de Janeiro, RJ null",
                                        "website": "https://www.flamengo.com.br/pagina-inicial-basquete",
                                        "founded": 1919,
                                        "clubColors": "Red / Black / White",
                                        "venue": "Estadio Jornalista Mário Filho",
                                        "lastUpdated": "2022-04-06T13:13:43Z"
                                    }
                                },
                                "id": 298147,
                                "utcDate": "2021-01-06T22:15:00Z",
                                "status": "FINISHED",
                                "matchday": 28,
                                "stage": "REGULAR_SEASON",
                                "group": "Regular Season",
                                "lastUpdated": "2021-01-09T13:32:38Z",
                                "homeTeam": {
                                    "id": 1770,
                                    "name": "Botafogo FR",
                                    "shortName": "Botafogo",
                                    "tla": "BOT",
                                    "crest": "https://crests.football-data.org/1770.png"
                                },
                                "awayTeam": {
                                    "id": 1768,
                                    "name": "CA Paranaense",
                                    "shortName": "Paranaense",
                                    "tla": "CAP",
                                    "crest": "https://crests.football-data.org/1768.png"
                                },
                                "score": {
                                    "winner": "AWAY_TEAM",
                                    "duration": "REGULAR",
                                    "fullTime": {
                                        "home": 0,
                                        "away": 2
                                    },
                                    "halfTime": {
                                        "home": 0,
                                        "away": 1
                                    }
                                },
                                "odds": {
                                    "msg": "Activate Odds-Package in User-Panel to retrieve odds."
                                },
                                "referees": [
                                    {
                                        "id": 11137,
                                        "name": "Rodrigo D'Alonso Ferreira",
                                        "type": "REFEREE",
                                        "nationality": "Brazil"
                                    }
                                ]
                            },
                            {
                                "area": {
                                    "id": 2032,
                                    "name": "Brazil",
                                    "code": "BRA",
                                    "flag": "https://crests.football-data.org/764.svg"
                                },
                                "competition": {
                                    "id": 2013,
                                    "name": "Campeonato Brasileiro Série A",
                                    "code": "BSA",
                                    "type": "LEAGUE",
                                    "emblem": "https://crests.football-data.org/bsa.png"
                                },
                                "season": {
                                    "id": 589,
                                    "startDate": "2020-08-09",
                                    "endDate": "2021-02-24",
                                    "currentMatchday": 38,
                                    "winner": {
                                        "id": 1783,
                                        "name": "CR Flamengo",
                                        "shortName": "Flamengo",
                                        "tla": "FLA",
                                        "crest": "https://crests.football-data.org/1783.png",
                                        "address": "null Rio de Janeiro, RJ null",
                                        "website": "https://www.flamengo.com.br/pagina-inicial-basquete",
                                        "founded": 1919,
                                        "clubColors": "Red / Black / White",
                                        "venue": "Estadio Jornalista Mário Filho",
                                        "lastUpdated": "2022-04-06T13:13:43Z"
                                    }
                                },
                                "id": 297963,
                                "utcDate": "2020-09-09T20:30:00Z",
                                "status": "FINISHED",
                                "matchday": 9,
                                "stage": "REGULAR_SEASON",
                                "group": "Regular Season",
                                "lastUpdated": "2020-09-12T08:32:38Z",
                                "homeTeam": {
                                    "id": 1768,
                                    "name": "CA Paranaense",
                                    "shortName": "Paranaense",
                                    "tla": "CAP",
                                    "crest": "https://crests.football-data.org/1768.png"
                                },
                                "awayTeam": {
                                    "id": 1770,
                                    "name": "Botafogo FR",
                                    "shortName": "Botafogo",
                                    "tla": "BOT",
                                    "crest": "https://crests.football-data.org/1770.png"
                                },
                                "score": {
                                    "winner": "DRAW",
                                    "duration": "REGULAR",
                                    "fullTime": {
                                        "home": 1,
                                        "away": 1
                                    },
                                    "halfTime": {
                                        "home": 0,
                                        "away": 0
                                    }
                                },
                                "odds": {
                                    "msg": "Activate Odds-Package in User-Panel to retrieve odds."
                                },
                                "referees": [
                                    {
                                        "id": 11266,
                                        "name": "Vinicius Gonçalves Dias Araujo",
                                        "type": "REFEREE",
                                        "nationality": "Brazil"
                                    },
                                    {
                                        "id": 11468,
                                        "name": "Marcelo Carvalho Van Gasse",
                                        "type": "REFEREE",
                                        "nationality": "Brazil"
                                    },
                                    {
                                        "id": 11257,
                                        "name": "Miguel Cataneo Ribeiro Da Costa",
                                        "type": "REFEREE",
                                        "nationality": "Brazil"
                                    },
                                    {
                                        "id": 60911,
                                        "name": "Luiz Alexandre Fernandes",
                                        "type": "REFEREE",
                                        "nationality": "Brazil"
                                    }
                                ]
                            }
                        ]
                    }
                    """;
            return generateQuizFromHead2Head(json);
        } else {
            Match match = matchRepository.findById(matchId)
                    .orElseThrow(() -> new IllegalArgumentException("Match not found with id: " + matchId));

            Team homeTeam = match.getHomeTeam();
            Team awayTeam = match.getAwayTeam();
            Team winner = match.getWinner();

            CompletableFuture<QuestionDto> scoreFuture = generateScoreQuestion(match);
            CompletableFuture<QuestionDto> winnerFuture = generateWinnerQuestion(match, homeTeam, awayTeam, winner);
            CompletableFuture<QuestionDto> playerFuture = generatePlayerQuestion(winner);

            List<QuestionDto> quiz = new ArrayList<>();

            try {
                quiz.add(scoreFuture.get());
                quiz.add(winnerFuture.get());
                QuestionDto playerQuestion = playerFuture.get();
                if (playerQuestion != null) quiz.add(playerQuestion);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return quiz;
        }
    }

    public List<QuestionDto> generateQuizFromHead2Head(String head2HeadJson) {
        List<QuestionDto> quiz = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(head2HeadJson);

            // Получаем команды
            JsonNode homeTeamNode = root.path("matches").get(0).path("homeTeam");
            JsonNode awayTeamNode = root.path("matches").get(0).path("awayTeam");

            String homeTeamName = homeTeamNode.path("name").asText();
            String awayTeamName = awayTeamNode.path("name").asText();

            QuestionDto scoreQuestion = generateScoreQuestionFromJson(root, homeTeamName, awayTeamName);
            quiz.add(scoreQuestion);

            QuestionDto winnerQuestion = generateWinnerQuestionFromJson(root, homeTeamName, awayTeamName);
            quiz.add(winnerQuestion);

        } catch (Exception e) {
            log.error("Ошибка генерации квиза из head2head JSON", e);
        }

        return quiz;
    }

    private QuestionDto generateScoreQuestionFromJson(JsonNode root, String home, String away) {
        // пример: спрашиваем "С каким счетом закончится матч?"
        String dateOfMatch = root.path("matches").get(0).path("utcDate").asText();


        Integer awayGoals = Integer.parseInt(root.path("matches").get(0).path("score").path("fullTime").path("away").asText());
        Integer homeGoals = Integer.parseInt(root.path("matches").get(0).path("score").path("fullTime").path("away").asText());
        List<String> options = List.of(
                (homeGoals + 1) + ":" + (awayGoals + 1),  homeGoals + ":" + awayGoals, (homeGoals + 3) + ":" + awayGoals,  "0:" + awayGoals
        );
        String correctAnswer = homeGoals+ ":"
                + awayGoals;
        return new QuestionDto("Какой счет матча " + home + " vs " + away + ", который состоялся " + dateOfMatch.substring(0, 10) + "?", options, correctAnswer);
    }

    private QuestionDto generateWinnerQuestionFromJson(JsonNode root, String home, String away) {
        List<String> options = List.of(home, away, "Ничья");
        int homeScore = root.path("matches").get(0).path("score").path("fuulTime").path("home").asInt();
        int awayScore = root.path("matches").get(0).path("score").path("fullTime").path("away").asInt();

        String correctAnswer;
        if (homeScore > awayScore) correctAnswer = home;
        else if (awayScore > homeScore) correctAnswer = away;
        else correctAnswer = "Ничья";
        String dateOfMatch = root.path("matches").get(0).path("utcDate").asText();

        return new QuestionDto("Кто держал победу в матче от "+ dateOfMatch.substring(0, 10) + "?", options, correctAnswer);
    }

    @Async
    public CompletableFuture<QuestionDto> generateScoreQuestion(Match match) {
        String score = match.getHomeScore() + " - " + match.getAwayScore();
        List<String> scoreOptions = generateScoreOptions(match.getHomeScore(), match.getAwayScore());
        return CompletableFuture.completedFuture(new QuestionDto(
                "С каким счётом закончился матч между " + match.getHomeTeam().getName() + " и " + match.getAwayTeam().getName() +
                        ", который состоялся " + match.getUtcDate().toLocalDate() + "?",
                scoreOptions,
                score
        ));
    }

    @Async
    public CompletableFuture<QuestionDto> generateWinnerQuestion(Match match, Team home, Team away, Team winner) {
        String winnerAnswer = match.getHomeScore().equals(match.getAwayScore()) ? "Ничья" : winner.getName();
        List<String> winnerOptions = generateWinnerOptions(home.getName(), away.getName(), match.getHomeScore(), match.getAwayScore());

        return CompletableFuture.completedFuture(new QuestionDto(
                "Какая команда победила в матче между " + home.getName() + " и " + away.getName() + "?",
                winnerOptions,
                winnerAnswer
        ));
    }

    @Async
    public CompletableFuture<QuestionDto> generatePlayerQuestion(Team winner) {
        List<Player> winnerPlayers = playerRepository.findByTeamId(winner.getId());
        Optional<Player> playerOpt = findShirtNumberPlayer(winnerPlayers, List.of(
                1, 2, 3, 4, 5, 6, 10, 9, 11, 7, 8, 6, 5, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23));

        if (playerOpt.isPresent()) {
            Player target = playerOpt.get();
            List<String> nameOptions = generatePlayerOptions(winnerPlayers, target.getName());

            return CompletableFuture.completedFuture(new QuestionDto(
                    "Кто из представителей команды " + winner.getName() + " играл под номером " + target.getShirtNumber() + "?",
                    nameOptions,
                    target.getName()
            ));
        }

        return CompletableFuture.completedFuture(null);
    }

//    public List<QuestionDto> generateQuizForMatch(Long matchId) {
//        Match match = matchRepository.findById(matchId)
//                .orElseThrow(() -> new IllegalArgumentException("Match not found with id: " + matchId));
//
//        Team homeTeam = match.getHomeTeam();
//        Team awayTeam = match.getAwayTeam();
//        Team winner = match.getWinner();
//
//        List<QuestionDto> quiz = new ArrayList<>();
//
//        // Вопрос 1: счёт матча
//        String score = match.getHomeScore() + " - " + match.getAwayScore();
//        List<String> scoreOptions = generateScoreOptions(match.getHomeScore(), match.getAwayScore());
//        quiz.add(new QuestionDto(
//                "С каким счётом закончился матч между " + homeTeam.getName() + " и " + awayTeam.getName() + ", который датируется " + match.getUtcDate().toLocalDate() + "?",
//                scoreOptions,
//                score
//        ));
//
//        // Вопрос 2: победитель
//        String winnerAnswer;
//        if (match.getHomeScore().equals(match.getAwayScore())) {
//            winnerAnswer = "Ничья";
//        } else {
//            winnerAnswer = winner.getName();
//        }
//
//        List<String> winnerOptions = generateWinnerOptions(homeTeam.getName(), awayTeam.getName(), match.getHomeScore(), match.getAwayScore());
//        quiz.add(new QuestionDto(
//                "Какая команда победила в матче между " + homeTeam.getName() + " и " + awayTeam.getName() + "?",
//                winnerOptions,
//                winnerAnswer
//        ));
//
//        // Вопрос 3: игрок под номером
//        System.out.println(winner.getId());
//        List<Player> winnerPlayers = playerRepository.findByTeamId(winner.getId());
//        System.out.println(winnerPlayers.size());
//        Optional<Player> playerOpt = findShirtNumberPlayer(winnerPlayers, List.of(1,2,3,4,5,6,10, 9, 11, 7, 8, 6, 5,12,13,14,15,16,17,18,19,20,21,22,23));
//
//        if (playerOpt.isPresent()) {
//            Player target = playerOpt.get();
//            List<String> nameOptions = generatePlayerOptions(winnerPlayers, target.getName());
//
//            quiz.add(new QuestionDto(
//                    "Кто из представителей команды " + winner.getName() + " играл под номером " + target.getShirtNumber() + "?",
//                    nameOptions,
//                    target.getName()
//            ));
//        }
//
//        return quiz;
//    }

    private List<String> generateScoreOptions(int homeScore, int awayScore) {
        Set<String> options = new HashSet<>();
        Random rand = new Random();
        options.add(homeScore + " - " + awayScore);

        while (options.size() < 3) {
            int randomHome = rand.nextInt(5);
            int randomAway = rand.nextInt(5);
            options.add(randomHome + " - " + randomAway);
        }

        List<String> list = new ArrayList<>(options);
        Collections.shuffle(list);
        return list;
    }

    private List<String> generateWinnerOptions(String home, String away, int homeScore, int awayScore) {
        Set<String> options = new HashSet<>(Arrays.asList(home, away, "Ничья"));
        List<String> list = new ArrayList<>(options);
        Collections.shuffle(list);
        return list;
    }

    private Optional<Player> findShirtNumberPlayer(List<Player> players, List<Integer> preferredNumbers) {
        for (int number : preferredNumbers) {
            for (Player p : players) {
                System.out.println("Checking player: " + p.getName() + ", Number: " + p.getShirtNumber());
                if (p.getShirtNumber() != null && p.getShirtNumber().equals(number)) {
                    System.out.println("Found matching player with number: " + number);
                    return Optional.of(p);
                }
            }
        }
        System.out.println("Preferred number not found, picking first available...");
        return players.stream().filter(p -> p.getShirtNumber() != null).findFirst();
    }


    private List<String> generatePlayerOptions(List<Player> players, String correctName) {
        Set<String> options = new HashSet<>();
        options.add(correctName);
        Random rand = new Random();

        while (options.size() < 3) {
            Player p = players.get(rand.nextInt(players.size()));
            if (p.getName() != null) {
                options.add(p.getName());
            }
        }

        List<String> result = new ArrayList<>(options);
        Collections.shuffle(result);
        return result;
    }
}
