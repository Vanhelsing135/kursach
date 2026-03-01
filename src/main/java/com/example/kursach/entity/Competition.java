package com.example.kursach.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Competition")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@ToString
public class Competition {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String type;

    @Column(name = "emblem_url")
    private String emblemUrl;

    @Column(nullable = false)
    private String country;

    private String plan;

    @Column(name = "current_season_id")
    private Long seasonId;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @ManyToMany(mappedBy = "runningCompetitions")
    @JsonIgnore
    private List<Team> teams;
}
