package com.pl.skijumping.service;

import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.domain.entity.QDataRace;
import com.pl.skijumping.domain.repository.DataRaceRepository;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.service.mapper.DataRaceMapper;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataRaceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataRaceService.class);
    private final DataRaceRepository dataRaceRepository;
    private final DataRaceMapper dataRaceMapper;

    public DataRaceService(DataRaceRepository dataRaceRepository, DataRaceMapper dataRaceMapper) {
        this.dataRaceRepository = dataRaceRepository;
        this.dataRaceMapper = dataRaceMapper;
    }

    public List<DataRaceDTO> findAll() {
        List<DataRace> dataRaceList = dataRaceRepository.findAll();
        if (dataRaceList == null) {
            return new ArrayList<>();
        }

        return dataRaceMapper.toDTO(dataRaceList);
    }

//    public DataRaceDTO saveOrUpdate(DataRaceDTO dataRaceDTO) {
//        QDataRace qDataRace = QDataRace.dataRace;
//        findByParams(dataRaceDTO)
//        BooleanExpression booleanExpression = qDataRace.id.eq(id);
//        dataRaceRepository.findAll(booleanExpression);
//    }


    public Optional<DataRaceDTO> findByParams(DataRaceDTO dataRaceDTO) {
        if (dataRaceDTO == null) {
            return Optional.empty();
        }
        QDataRace qDataRace = QDataRace.dataRace;
        BooleanExpression booleanExpression =
                qDataRace.date.eq(dataRaceDTO.getDate())
                        .and(qDataRace.city.eq(dataRaceDTO.getCity()))
                        .and(qDataRace.shortCountryName.eq(dataRaceDTO.getShortCountryName()))
                        .and(qDataRace.raceId.eq(dataRaceDTO.getRaceId()));

        DataRace dataRace = (DataRace) dataRaceRepository.findOne(booleanExpression);

        if(dataRace == null) {
            return Optional.empty();
        }

        return Optional.of(dataRaceMapper.toDTO(dataRace));
    }

}