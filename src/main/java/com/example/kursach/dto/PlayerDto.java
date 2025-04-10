package com.example.kursach.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerDto {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    private String nationality;

    private String position;

    @Column(name = "shirt_number")
    private Integer shirtNumber;

    @Column(name = "last_updated")
    private String lastUpdated;

    private CurTeamDto currentTeam;

    @Data
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurTeamDto {
        private Long id;
        private String name;
        private String shortName;
        private String tla;
        private String crest;
        private String address;
        private int founded;
        private String clubColors;
        private String venue;
        private List<CompetitionDto> runningCompetitions;
        private AreaDto area;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class AreaDto {
            private String name;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class CompetitionDto {
            private Long id;
            private String name;
            private String code;
            private String type;
            private String emblem;
        }
    }
}
