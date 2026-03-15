package com.example.kursach.exceptionHandler;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiError {
    private HttpStatus status;
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
}