package com.example.kursach.repository;

import com.example.kursach.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Override
    Optional<Answer> findById(Long aLong);
}
