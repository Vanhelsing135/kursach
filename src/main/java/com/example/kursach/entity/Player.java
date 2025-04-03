package com.example.kursach.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.sql.Timestamp;

@Entity
@Table(name = "Player")
@Getter
@Setter
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String nationality;

    private String position;

    private Integer shirtNumber;

    private Timestamp lastUpdated;
}
