package com.example.kursach.service;

import com.example.kursach.entity.Team;
import com.example.kursach.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    @Transactional
    public List<Team> getTeams(Pageable pageable) {
        return teamRepository.findAll(pageable).stream().toList();
    }

    @Transactional
    public Team getTeamInfo(Long id) {
        return teamRepository.findById(id).get();
    }
}