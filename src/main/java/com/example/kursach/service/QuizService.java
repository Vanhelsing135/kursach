package com.example.kursach.service;

import com.example.kursach.dto.QuestionDto;
import com.example.kursach.entity.Match;
import com.example.kursach.entity.Player;
import com.example.kursach.entity.Question;
import com.example.kursach.entity.Team;
import com.example.kursach.repository.MatchRepository;
import com.example.kursach.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    public List<QuestionDto> generateQuizForMatch(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Match not found with id: " + matchId));

        Team homeTeam = match.getHomeTeam();
        Team awayTeam = match.getAwayTeam();
        Team winner = match.getWinner();

        List<QuestionDto> quiz = new ArrayList<>();

        // Вопрос 1: счёт матча
        String score = match.getHomeScore() + " - " + match.getAwayScore();
        List<String> scoreOptions = generateScoreOptions(match.getHomeScore(), match.getAwayScore());
        quiz.add(new QuestionDto(
                "С каким счётом закончился матч между " + homeTeam.getName() + " и " + awayTeam.getName() + ", который датируется " + match.getUtcDate().toLocalDate() + "?",
                scoreOptions,
                score
        ));

        // Вопрос 2: победитель
        String winnerAnswer;
        if (match.getHomeScore().equals(match.getAwayScore())) {
            winnerAnswer = "Ничья";
        } else {
            winnerAnswer = winner.getName();
        }

        List<String> winnerOptions = generateWinnerOptions(homeTeam.getName(), awayTeam.getName(), match.getHomeScore(), match.getAwayScore());
        quiz.add(new QuestionDto(
                "Какая команда победила в матче между " + homeTeam.getName() + " и " + awayTeam.getName() + "?",
                winnerOptions,
                winnerAnswer
        ));

        // Вопрос 3: игрок под номером
        System.out.println(winner.getId());
        List<Player> winnerPlayers = playerRepository.findByTeamId(winner.getId());
        System.out.println(winnerPlayers.size());
        Optional<Player> playerOpt = findShirtNumberPlayer(winnerPlayers, List.of(1,2,3,4,5,6,10, 9, 11, 7, 8, 6, 5,12,13,14,15,16,17,18,19,20,21,22,23));

        if (playerOpt.isPresent()) {
            Player target = playerOpt.get();
            List<String> nameOptions = generatePlayerOptions(winnerPlayers, target.getName());

            quiz.add(new QuestionDto(
                    "Кто из представителей команды " + winner.getName() + " играл под номером " + target.getShirtNumber() + "?",
                    nameOptions,
                    target.getName()
            ));
        }

        return quiz;
    }

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
