package com.example.kursach.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.sql.Timestamp;

@Entity
@Table(name = "Player")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Player {

    @Id
    private Long id;

    @Column(name = "team_id")
    private Long teamId;

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
}
