package com.example.kursach.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuestionDto {
    private String questionText;
    private List<String> options;
    private String correctAnswer;
}
