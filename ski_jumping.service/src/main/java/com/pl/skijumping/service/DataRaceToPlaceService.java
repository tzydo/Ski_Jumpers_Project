package com.pl.skijumping.service;

import com.pl.skijumping.domain.entity.DataRaceToPlace;
import com.pl.skijumping.domain.repository.DataRaceToPlaceRepository;
import com.pl.skijumping.dto.DataRaceToPlaceDTO;
import com.pl.skijumping.service.mapper.DataRaceToPlaceMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataRaceToPlaceService {
    private final DataRaceToPlaceMapper dataRaceToPlaceMapper;
    private final DataRaceToPlaceRepository dataRaceToPlaceRepository;

    public DataRaceToPlaceService(DataRaceToPlaceMapper dataRaceToPlaceMapper,
                                  DataRaceToPlaceRepository dataRaceToPlaceRepository) {
        this.dataRaceToPlaceMapper = dataRaceToPlaceMapper;
        this.dataRaceToPlaceRepository = dataRaceToPlaceRepository;
    }

    public DataRaceToPlaceDTO save(DataRaceToPlaceDTO dataRaceToPlaceDTO) {
        DataRaceToPlace dataRaceToPlace = dataRaceToPlaceRepository.save(dataRaceToPlaceMapper.fromDTO(dataRaceToPlaceDTO));
        return dataRaceToPlaceMapper.toDTO(dataRaceToPlace);
    }

    public Optional<DataRaceToPlaceDTO> findByDataRaceToPlace(DataRaceToPlaceDTO dataRaceToPlaceDTO) {
        DataRaceToPlace dataRaceToPlace = dataRaceToPlaceRepository.findByDataRace_IdAndPlace_Id(dataRaceToPlaceDTO.getDataRaceId(), dataRaceToPlaceDTO.getPlaceId());
        return Optional.ofNullable(dataRaceToPlaceMapper.toDTO(dataRaceToPlace));
    }
}
