package com.example.kursach.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class MatchMyResponseDto {
    private Long id;

    private LocalDateTime utcDate;

    private String status;

    private String venue;

    private Integer matchday;

    private String stage;

    private String group;

    private LocalDateTime lastUpdated;

    private Long homeTeamId;

    private Long awayTeamId;

    private Integer homeScore;

    private Integer awayScore;


    private Long winnerId;

    private String competitionName;

    private Long seasonId;
}
