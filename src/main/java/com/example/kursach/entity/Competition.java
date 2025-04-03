package com.example.kursach.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "Competition")
@Getter
@Setter
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String type;

    private String emblemUrl;

    @Column(nullable = false)
    private String country;

    private String plan;

    @ManyToOne
    @JoinColumn(name = "current_season_id")
    private Season currentSeason;

    private Timestamp lastUpdated;
}
