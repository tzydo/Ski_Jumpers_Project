package com.pl.skijumping.service;

import com.pl.skijumping.domain.repository.DataRaceToPlaceRepository;
import com.pl.skijumping.service.mapper.DataRaceToPlaceMapper;
import org.springframework.stereotype.Service;

@Service
public class DataRaceToPlaceService {
    private final DataRaceToPlaceMapper dataRaceToPlaceMapper;
    private final DataRaceToPlaceRepository dataRaceToPlaceRepository;

    public DataRaceToPlaceService(DataRaceToPlaceMapper dataRaceToPlaceMapper,
                                  DataRaceToPlaceRepository dataRaceToPlaceRepository) {
        this.dataRaceToPlaceMapper = dataRaceToPlaceMapper;
        this.dataRaceToPlaceRepository = dataRaceToPlaceRepository;
    }
}
