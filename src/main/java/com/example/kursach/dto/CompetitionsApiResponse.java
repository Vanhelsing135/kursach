package com.example.kursach.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompetitionsApiResponse {
    private int count;
    private Filters filters;
    private List<Competition> competitions;

    // Геттеры и сеттеры
@Getter
@Setter
    public static class Filters {
        private String client;

        // Геттеры и сеттеры
    }

    @Getter
    @Setter
    public static class Competition {
        private Long id;
        private Area area;
        private String name;
        private String code;
        private String type;
        private String emblem;
        private String plan;
        private CurrentSeason currentSeason;
        private int numberOfAvailableSeasons;
        private String lastUpdated;

        // Геттеры и сеттеры
@Getter
@Setter
        public static class Area {
            private Long id;
            private String name;
            private String code;
            private String flag;

            // Геттеры и сеттеры
        }

        @Getter
        @Setter
        public static class CurrentSeason {
            private Long id;
            private String startDate;
            private String endDate;
            private int currentMatchday;
            private Winner winner;

            // Геттеры и сеттеры

            @Getter
            @Setter
            public static class Winner {
                private int id;
                private String name;
                private String shortName;
                private String tla;
                private String crest;
                private String address;
                private String website;
                private int founded;
                private String clubColors;
                private String venue;
                private String lastUpdated;

                // Геттеры и сеттеры
            }
        }
    }
}
