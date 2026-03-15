package com.example.kursach.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableDto {
    private Long teamId;
    private String teamName;
    private String teamLogo;
    private int position;
    private int points;
    private int matchesPlayed;
    private int lost;
    private int won;
    private int draw;
    private int goalAgainst;
    private int goalFor;
    private int goalDifference;
}