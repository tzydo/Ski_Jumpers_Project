package com.pl.skijumping.service;

import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.domain.repository.DataRaceRepository;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.service.mapper.DataRaceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataRaceService {
    private final DataRaceRepository dataRaceRepository;
    private final DataRaceMapper dataRaceMapper;

    public DataRaceService(DataRaceRepository dataRaceRepository,
                           DataRaceMapper dataRaceMapper) {
        this.dataRaceRepository = dataRaceRepository;
        this.dataRaceMapper = dataRaceMapper;
    }

    @Transactional
    public Optional<DataRaceDTO> findById(Long id) {
        DataRace dataRace = dataRaceRepository.findOne(id);
        return Optional.ofNullable(dataRaceMapper.toDTO(dataRace));
    }

    @Transactional
    public Optional<DataRaceDTO> findByEventId(Long id) {
        DataRace dataRace = dataRaceRepository.findByEventId(id);
        return Optional.ofNullable(dataRaceMapper.toDTO(dataRace));
    }

    @Transactional
    public List<DataRaceDTO> findAll() {
        List<DataRace> dataRaceList = dataRaceRepository.findAll();
        if (dataRaceList == null) {
            return new ArrayList<>();
        }

        return dataRaceMapper.toDTO(dataRaceList);
    }

    @Transactional
    public DataRaceDTO save(DataRaceDTO dataRaceDTO) {
        if (dataRaceDTO == null) {
            return null;
        }

        return dataRaceMapper.toDTO(dataRaceRepository.save(dataRaceMapper.fromDTO(dataRaceDTO)));
    }

    @Transactional
    public Optional<DataRaceDTO> findByRaceId(Long raceId) {
        if (raceId == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(dataRaceMapper.toDTO(dataRaceRepository.findByRaceId(raceId)));
    }
}