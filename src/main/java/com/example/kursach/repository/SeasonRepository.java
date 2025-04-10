package com.example.kursach.repository;

import com.example.kursach.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season, Long> {
    boolean existsById(Long id);
}
