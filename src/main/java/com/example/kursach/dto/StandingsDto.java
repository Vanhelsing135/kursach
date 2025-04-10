package com.example.kursach.dto;

import com.example.kursach.entity.Area;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StandingsDto {
    private Area area;
    private CompetitionDto competition;
    private Season season;
    private List<Standings> standings;

    @Data
    public static class CompetitionDto {
        private Long id;
        private String name;
        private String code;
        private String type;
        private String emblem;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Season {
        private Long id;
        private String startDate;
        private String endDate;
//        private int currentMatch;
    }

    @Data
    public static class Standings {
        private String stage;
        private String type;
        private String group;
        private List<Table> table;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Table {

            private int position;
            private TeamDto team;
            private int playedGames;
            private int won;
            private int draw;
            private int lost;
            private int points;
            private int goalsFor;
            private int goalsAgainst;
            private int goalDifference;
            private String form;

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class TeamDto {
                private Long id;
                private String name;
                private String shortName;
                private String tla;
            }

        }
    }


}
