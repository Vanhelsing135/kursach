package com.example.kursach.controller;

import com.example.kursach.dto.QuestionDto;
import com.example.kursach.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kursach/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping("/{matchId}")
    public ResponseEntity<List<QuestionDto>> getQuiz(@PathVariable Long matchId) {
        return ResponseEntity.ok(quizService.generateQuizForMatch(matchId));
    }


}
