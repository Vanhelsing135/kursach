package com.example.kursach.dto;

import com.example.kursach.entity.Area;
import com.example.kursach.entity.Competition;
import com.example.kursach.entity.Season;
import com.example.kursach.entity.Team;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamApiResponse {
    private int count;
    private CompetitionDto competition;
    private SeasonDto season;
    private List<TeamDto> teams;

    @Data
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CompetitionDto {
        private Long id;
        private String name;
        private String code;
        private String type;
        private String emblem;
    }

    @Data
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SeasonDto {
        private Long id;
        private String startDate;
        private String endDate;
        private int currentMatchday;
    }

    @Data
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class TeamDto {
        private Long id;
        private String name;
        private String shortName;
        private String tla;
        private String crest;
        private String address;
        private String website;
        private int founded;
        private String clubColors;
        private String venue;
        private Area area;
        private List<CompetitionDto> runningCompetitions;
        private CoachDto coach;
        private List<SquadMemberDto> squad;

        @Data
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)

        public static class AreaDto {
            private Long id;
            private String name;
            private String code;
            private String flag;
        }

        @Data
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)

        public static class CoachDto {
            private Long id;
            private String firstName;
            private String lastName;
            private String name;
            private String dateOfBirth;
            private String nationality;
            private ContractDto contract;

            @Data
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)

            public static class ContractDto {
                private String start;
                private String until;
            }
        }

        @Data
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)

        public static class SquadMemberDto {
            private Long id;
            private String name;
            private String position;
            private String dateOfBirth;
            private String nationality;
        }
    }
}
