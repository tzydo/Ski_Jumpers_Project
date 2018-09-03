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

    public Optional<List<SkiJumperDTO>> findAllByName(String name) {
        List<SkiJumperDTO> skiJumperDTOList = skiJumperMapper.toDTO(skiJumperRepository.findAllByName(name));
        if (skiJumperDTOList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(skiJumperDTOList);
    }

    public Optional<SkiJumperDTO> findOneByName(String name) {
        SkiJumperDTO skiJumperDTO = skiJumperMapper.toDTO(skiJumperRepository.findOneByName(name));
        return Optional.ofNullable(skiJumperDTO);
    }

    public Optional<SkiJumperDTO> findById(Long id) {
        SkiJumperDTO skiJumperDTO = skiJumperMapper.toDTO(skiJumperRepository.findOne(id));
        if (skiJumperDTO == null) {
            return Optional.empty();
        }
        return Optional.of(skiJumperDTO);
    }

    public Boolean deleteAll() {
        try {
            skiJumperRepository.deleteAll();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public long getJumpersCount() {
        return skiJumperRepository.count();
    }

    public void update(SkiJumperDTO skiJumperDTO) {
        if(skiJumperDTO != null) {
            skiJumperRepository.save(skiJumperMapper.fromDTO(skiJumperDTO));
        }
    }
}
