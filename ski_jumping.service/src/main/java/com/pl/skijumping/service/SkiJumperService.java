package com.pl.skijumping.service;

import com.pl.skijumping.domain.entity.SkiJumper;
import com.pl.skijumping.domain.repository.SkiJumperRepository;
import com.pl.skijumping.dto.SkiJumperDTO;
import com.pl.skijumping.service.mapper.SkiJumperMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SkiJumperService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SkiJumperService.class);
    private final SkiJumperRepository skiJumperRepository;
    private final SkiJumperMapper skiJumperMapper;

    public SkiJumperService(SkiJumperRepository skiJumperRepository, SkiJumperMapper skiJumperMapper) {
        this.skiJumperRepository = skiJumperRepository;
        this.skiJumperMapper = skiJumperMapper;
    }

    public SkiJumperDTO save(SkiJumperDTO skiJumperDTO) {
        if (skiJumperDTO == null) {
            LOGGER.error("Cannot save null year!");
            return null;
        }

        SkiJumper skiJumper = skiJumperRepository.save(skiJumperMapper.fromDTO(skiJumperDTO));
        return skiJumperMapper.toDTO(skiJumper);
    }

    @Transactional(readOnly = true)
    public List<SkiJumperDTO> findAll() {
        List<SkiJumperDTO> skiJumperDTOS = skiJumperMapper.toDTO(skiJumperRepository.findAll());
        return skiJumperDTOS.isEmpty() ? new ArrayList<>() : skiJumperDTOS;
    }

    @Transactional
    public Optional<SkiJumperDTO> findByFisCode(Integer fisCode) {
        SkiJumper skiJumper = skiJumperRepository.findByFisCode(fisCode);
        if(skiJumper == null) {
            return Optional.empty();
        }
        return Optional.of(skiJumperMapper.toDTO(skiJumper));
    }
}
