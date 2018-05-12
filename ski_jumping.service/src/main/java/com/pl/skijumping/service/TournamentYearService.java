package com.pl.skijumping.service;

import com.pl.skijumping.domain.dto.TournamentYearDTO;
import com.pl.skijumping.domain.entity.TournamentYear;
import com.pl.skijumping.domain.repository.TournamentYearRepository;
import com.pl.skijumping.domain.dto.TournamentYearDTO;
import com.pl.skijumping.domain.entity.TournamentYear;
import com.pl.skijumping.domain.mapper.TournamentYearMapper;
import com.pl.skijumping.domain.repository.TournamentYearRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TournamentYearService {
    private final TournamentYearRepository tournamentYearRepository;
    private final TournamentYearMapper tournamentYearMapper;

    public TournamentYearService(TournamentYearRepository tournamentYearRepository,
                                 TournamentYearMapper tournamentYearMapper) {
        this.tournamentYearRepository = tournamentYearRepository;
        this.tournamentYearMapper = tournamentYearMapper;
    }

    public Optional<TournamentYearDTO> save(TournamentYearDTO tournamentYearDTO) {
        if (tournamentYearDTO == null) {
            return Optional.empty();
        }
        TournamentYear tournamentYear = this.tournamentYearRepository.save(
                tournamentYearMapper.fromDTO(tournamentYearDTO));
        return Optional.of(tournamentYearMapper.toDTO(tournamentYear));
    }

    public Optional<List<TournamentYearDTO>> findAll() {
        List<TournamentYear> tournamentYears = tournamentYearRepository.findAll();
        if(tournamentYears == null) {
            return Optional.empty();
        }
        return Optional.of(tournamentYearMapper.toDTO(tournamentYears));
    }
}
