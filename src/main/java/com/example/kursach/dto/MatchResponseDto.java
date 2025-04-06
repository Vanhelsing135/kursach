package com.example.kursach.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchResponseDto {
    private List<MatchDto> matches;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MatchDto {
        private CompetitionDto competition;
        private SeasonDto season;
        private AreaDto area;
        private Long id;
        private String utcDate;
        private String status;
        private String stage;
        private String group;
        private Score score;
        private String lastUpdated;
        private TeamDto homeTeam;
        private TeamDto awayTeam;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class CompetitionDto {
            private Long id;
            private String name;
            private String code;
            private String type;
            private String emblem;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class SeasonDto {
            private Long id;
            private String startDate;
            private String endDate;
            private TeamDto winner;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class AreaDto {
            private Long id;
            private String name;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class TeamDto {
            private Long id;
            private String name;
            private String shortName;
            private String tla;
            private String crest;
            private Score score;

        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Score {
            private String winner;
            private FullTimeDto fullTime;

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class FullTimeDto {
                private int home;
                private int away;
            }
        }
    }
}
