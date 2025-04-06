package com.example.kursach.repository;

import com.example.kursach.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    Optional<Competition> findByCode(String code);

    List<Competition> findByLastUpdatedBefore(LocalDateTime dateTime);
}
