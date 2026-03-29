package com.example.kursach.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false)
    private boolean isVerified = false;

    @Column(length = 255)
    private String tokenEmail;

    @ManyToMany
    @JoinTable(
            name = "Favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> favoriteTeams = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "user_favorite_teams", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "team_id")
    private List<Integer> favTeams = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "user_favorite_competitions", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "competition_id")
    private List<Integer> favoriteCompetitions = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
