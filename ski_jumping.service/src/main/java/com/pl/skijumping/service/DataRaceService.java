package com.pl.skijumping.service;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.domain.entity.QDataRace;
import com.pl.skijumping.domain.repository.DataRaceRepository;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.service.mapper.DataRaceMapper;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Service;

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
        BooleanExpression booleanExpression =
                qDataRace.date.eq(dataRace.getDate())
                        .and(qDataRace.city.eq(dataRace.getCity()))
                        .and(qDataRace.shortCountryName.eq(dataRace.getShortCountryName()))
                        .and(qDataRace.raceId.eq(dataRace.getRaceId()))
                        .and(qDataRace.competitionName().eq(dataRace.getCompetitionName()))
                        .and(qDataRace.competitionType().eq(dataRace.getCompetitionType()));

        DataRace foundDataRace = (DataRace) dataRaceRepository.findOne(booleanExpression);

        if (foundDataRace == null) {
            return Optional.empty();
        }

        return Optional.of(foundDataRace);
    }

    public List<Long> getRaceDataIds() {
        List<Long> raceDataList = dataRaceRepository.getRaceDataList();
        if(raceDataList.isEmpty()) {
            return new ArrayList<>();
        }

        return raceDataList;
    }
}