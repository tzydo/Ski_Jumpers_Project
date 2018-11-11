package com.pl.skijumping.service;

import com.pl.skijumping.domain.entity.TournamentYear;
import com.pl.skijumping.domain.repository.TournamentYearRepository;
import com.pl.skijumping.dto.TournamentYearDTO;
import com.pl.skijumping.service.mapper.TournamentYearMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentYearService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentYearService.class);
    private final TournamentYearRepository tournamentYearRepository;
    private final TournamentYearMapper tournamentYearMapper;

    public TournamentYearService(TournamentYearRepository tournamentYearRepository,
                                 TournamentYearMapper tournamentYearMapper) {
        this.tournamentYearRepository = tournamentYearRepository;
        this.tournamentYearMapper = tournamentYearMapper;
    }

    public Optional<TournamentYearDTO> update(TournamentYearDTO tournamentYearDTO) {
        if (tournamentYearDTO == null || tournamentYearDTO.getYear() == null) {
            LOGGER.error("Cannot save null year!");
            return Optional.empty();
        }
        TournamentYear tournamentByYear = tournamentYearRepository.findByYear(tournamentYearDTO.getYear());
        if (tournamentByYear == null) {
            LOGGER.info(String.format("Saving new competition year: %s", tournamentYearDTO.getYear()));
            tournamentByYear = this.tournamentYearRepository.save(tournamentYearMapper.fromDTO(tournamentYearDTO));
        } else {
            LOGGER.info(String.format("The year: %s of the competition already exists in the database", tournamentYearDTO.getYear()));
        }

        return Optional.of(tournamentYearMapper.toDTO(tournamentByYear));
    }

    public List<TournamentYearDTO> findAll() {
        List<TournamentYear> tournamentYears = tournamentYearRepository.findAll();
        if (tournamentYears == null) {
            return new ArrayList<>();
        }
        return tournamentYearMapper.toDTO(tournamentYears);
    }

    public List<TournamentYearDTO> findAllByTop(Integer limit) {
        if(limit == null) {
            return new ArrayList<>();
        }
        List<TournamentYear> byTopAndLimit = tournamentYearRepository.findAllByOrderByYearDesc(new PageRequest(0, limit));
        if (byTopAndLimit != null) {
            return tournamentYearMapper.toDTO(byTopAndLimit);
        } else {
            return new ArrayList<>();
        }
    }
}
