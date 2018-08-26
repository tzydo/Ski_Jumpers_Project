package com.pl.skijumping.service;

import com.pl.skijumping.domain.entity.CompetitionName;
import com.pl.skijumping.domain.entity.QCompetitionName;
import com.pl.skijumping.domain.repository.CompetitionNameRepository;
import com.pl.skijumping.dto.CompetitionNameDTO;
import com.pl.skijumping.service.mapper.CompetitionNameMapper;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetitionNameService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompetitionNameService.class);
    private final CompetitionNameRepository competitionNameRepository;
    private final CompetitionNameMapper competitionNameMapper;

    public CompetitionNameService(CompetitionNameRepository competitionNameRepository,
                                  CompetitionNameMapper competitionNameMapper) {
        this.competitionNameRepository = competitionNameRepository;
        this.competitionNameMapper = competitionNameMapper;
    }

    public CompetitionNameDTO save(CompetitionNameDTO competitionNameDTO) {
        if (competitionNameDTO == null) {
            LOGGER.error("Cannot save null competition type");
            return null;
        }

        CompetitionName competitionName = competitionNameMapper.fromDTO(competitionNameDTO);
        return competitionNameMapper.toDTO(competitionNameRepository.save(competitionName));
    }

    public Optional<CompetitionNameDTO> find(Long id) {
        if (id == null) {
            LOGGER.error("Cannot find competition type for null id");
            return Optional.empty();
        }

        CompetitionName competitionName = competitionNameRepository.findOne(id);
        if (competitionName == null) {
            return Optional.empty();
        }

        return Optional.of(competitionNameMapper.toDTO(competitionName));
    }

    public List<CompetitionNameDTO> findAll() {
        List<CompetitionName> competitionNameList = competitionNameRepository.findAll();
        return competitionNameMapper.toDTO(competitionNameList);
    }

    public Optional<CompetitionNameDTO> findByName(String name) {
        if (name == null) {
            return Optional.empty();
        }

        QCompetitionName qCompetitionName = QCompetitionName.competitionName;
        BooleanExpression pattern = qCompetitionName.name.eq(name);

        CompetitionName competitionName = (CompetitionName) competitionNameRepository.findOne(pattern);
        if (competitionName == null) {
            return Optional.empty();
        }

        return Optional.of(competitionNameMapper.toDTO(competitionName));
    }
}