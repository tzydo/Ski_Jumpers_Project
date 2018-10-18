package com.pl.skijumping.service;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.domain.entity.QDataRace;
import com.pl.skijumping.domain.repository.DataRaceRepository;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.service.mapper.DataRaceMapper;
import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataRaceService {
    private final DataRaceRepository dataRaceRepository;
    private final DataRaceMapper dataRaceMapper;
    private final DiagnosticMonitor diagnosticMonitor;

    public DataRaceService(DataRaceRepository dataRaceRepository,
                           DataRaceMapper dataRaceMapper,
                           DiagnosticMonitor diagnosticMonitor) {
        this.dataRaceRepository = dataRaceRepository;
        this.dataRaceMapper = dataRaceMapper;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Transactional
    public Optional<DataRaceDTO> findById(Long id) {
        DataRace dataRace = dataRaceRepository.findOne(id);
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

    public DataRaceDTO save(DataRaceDTO dataRaceDTO) {
        if (dataRaceDTO == null) {
            return null;
        }

        return dataRaceMapper.toDTO(dataRaceRepository.save(dataRaceMapper.fromDTO(dataRaceDTO)));
    }


    public DataRaceDTO update(DataRaceDTO dataRaceDTO) {
        if (dataRaceDTO == null) {
            return null;
        }

        DataRace dataRace = dataRaceMapper.fromDTO(dataRaceDTO);
        Optional<DataRace> foundDataRace;
        try {
            foundDataRace = findByDataRace(dataRace);
        } catch (IllegalArgumentException e) {
            diagnosticMonitor.logError(
                    String.format("Cannot find object with null value!. Object: %s", dataRace.toString()), getClass());
            return null;
        }

        if (!foundDataRace.isPresent()) {
            return dataRaceMapper.toDTO(dataRaceRepository.save(dataRace));
        }

        return dataRaceMapper.toDTO(foundDataRace.get());
    }

    public DataRace save(DataRace dataRace) {
        if (dataRace == null) {
            return null;
        }

        Optional<DataRace> foundDataRace = findByDataRace(dataRace);
        return foundDataRace.orElseGet(() -> dataRaceRepository.save(dataRace));
    }

    public Optional<DataRace> findByDataRace(DataRace dataRace) {
        if (dataRace == null) {
            return Optional.empty();
        }

        QDataRace qDataRace = QDataRace.dataRace;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (dataRace.getDate() != null) booleanBuilder.and(qDataRace.date.eq(dataRace.getDate()));
        if (dataRace.getCity() != null) booleanBuilder.and(qDataRace.city.eq(dataRace.getCity()));
        if (dataRace.getShortCountryName() != null)
            booleanBuilder.and(qDataRace.shortCountryName.eq(dataRace.getShortCountryName()));
        if (dataRace.getRaceId() != null) booleanBuilder.and(qDataRace.raceId.eq(dataRace.getRaceId()));
        if (dataRace.getCompetitionName() != null)
            booleanBuilder.and(qDataRace.competitionName().eq(dataRace.getCompetitionName()));
        if (dataRace.getCompetitionType() != null)
            booleanBuilder.and(qDataRace.competitionType().eq(dataRace.getCompetitionType()));

        DataRace foundDataRace = (DataRace) dataRaceRepository.findOne(booleanBuilder);

        if (foundDataRace == null) {
            return Optional.empty();
        }

        return Optional.of(foundDataRace);
    }

    public List<Long> getRaceDataIds() {
        List<Long> raceDataList = dataRaceRepository.getRaceDataList();
        if (raceDataList.isEmpty()) {
            return new ArrayList<>();
        }

        return raceDataList;
    }
}