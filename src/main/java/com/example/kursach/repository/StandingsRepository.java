package com.example.kursach.repository;

import com.example.kursach.entity.Competition;
import com.example.kursach.entity.Standings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StandingsRepository  extends JpaRepository<Standings, Long> {
    List<Standings> findByCompetition(Competition competition);
}
