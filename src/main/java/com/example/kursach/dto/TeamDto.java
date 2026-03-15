package com.example.kursach.dto;


import com.example.kursach.entity.Competition;
import com.example.kursach.entity.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    private String name;
    private String shortName;
    private String tla;
    private String address;
    private Integer founded;
    private String clubColors;
    private String venue;
    private List<Pair<String,String>> runningCompetitions;
    private List<Pair<String, Pair<String, Integer>>> players;
    private String crest;
}