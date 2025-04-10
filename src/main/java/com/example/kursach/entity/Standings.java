package com.example.kursach.entity;

import com.example.kursach.entity.Competition;
import com.example.kursach.entity.Season;
import com.example.kursach.entity.Team;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Standings")
@Data
public class Standings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    @ManyToOne
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;

    private String type;
    private String stage;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    private int position;

    @Column(name = "played_games")
    private int playedGames;

    private int won;

    private int draw;

    private int lost;
    private String form;

    @Column(name = "goals_for")
    private int goalsFor;

    @Column(name = "goals_against")
    private int goalsAgainst;

    @Column(name = "goal_difference", insertable = false, updatable = false)
    private int goalDifference;

    private int points;
}
