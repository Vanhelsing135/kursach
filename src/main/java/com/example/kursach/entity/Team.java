package com.example.kursach.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "Team")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Team {

    @Id
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "short_name",length = 50)
    private String shortName;

    @Column(length = 10)
    private String tla;

    @Column(length = 255)
    private String address;

    private Integer founded;

    @Column(name = "club_colors", length = 100)
    private String clubColors;

    @Column(length = 255)
    private String venue;

    @ManyToMany
    @JoinTable(
            name = "team_competition",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "competition_id")
    )
    private List<Competition> runningCompetitions;

    @OneToMany(mappedBy = "teamId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> squad;
}
