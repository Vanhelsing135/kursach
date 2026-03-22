package com.example.kursach.controller;

import com.example.kursach.service.MatchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/kursach/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<String> getMatches(
            @RequestParam(required = false) String ids,
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int pageSize
    ) throws JsonProcessingException {

        log.info("Запрос матчей с фильтрами");

        String matches = matchService.getMatches(
                ids, dateFrom, dateTo, status, page, pageSize
        );

        return ResponseEntity.ok(matches);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<String> getMatchesDetail(@PathVariable Long id) {
        log.info("Получение деталей матча id: {}", id);
        String res = matchService.getMatchDetails(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping(value = "/{id}/head2head")
    public ResponseEntity<String> getStatisticsHead2Head(@PathVariable Long id) {
        log.info("Получение статистики личных встреч id: {}", id);
        String res = matchService.getStatisticsHead2Head(id);
        return ResponseEntity.ok(res);
    }
}