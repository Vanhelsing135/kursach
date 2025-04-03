package com.example.kursach.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "Team")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 50)
    private String shortName;

    @Column(length = 10, unique = true)
    private String tla;

    @Column(length = 255)
    private String address;

    private Integer founded;

    @Column(length = 100)
    private String clubColors;

    @Column(length = 255)
    private String venue;

    @ManyToMany(mappedBy = "favoriteTeams")
    private Set<User> usersWhoFavorited = new HashSet<>();
}
