package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.DataRaceToPlace;
import com.pl.skijumping.domain.repository.DataRaceRepository;
import com.pl.skijumping.domain.repository.PlaceRepository;
import com.pl.skijumping.dto.DataRaceToPlaceDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DataRaceToPlaceMapper {

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private DataRaceRepository dataRaceRepository;

    @Mappings({
            @Mapping(source = "dataRace.id", target = "dataRaceId"),
            @Mapping(source = "place.id", target = "placeId")
    })
    public abstract DataRaceToPlaceDTO toDTO(DataRaceToPlace dataRaceToPlace);

    public abstract List<DataRaceToPlaceDTO> toDTO(List<DataRaceToPlace> dataRaceToPlaceList);

    @InheritInverseConfiguration
    public abstract DataRaceToPlace fromDTO(DataRaceToPlaceDTO dataRaceToPlaceDTO);

    public abstract List<DataRaceToPlace> fromDTO(List<DataRaceToPlaceDTO> dataRaceToPlaceDTOList);

    @AfterMapping
    public DataRaceToPlace setPlace(@MappingTarget DataRaceToPlace dataRaceToPlace, DataRaceToPlaceDTO dataRaceToPlaceDTO) {
        if (dataRaceToPlaceDTO == null || dataRaceToPlaceDTO.getPlaceId() == null) {
            return dataRaceToPlace;
        }

        return dataRaceToPlace.place(placeRepository.findOne(dataRaceToPlaceDTO.getPlaceId()));
    }

    @AfterMapping
    public DataRaceToPlace setDataRace(@MappingTarget DataRaceToPlace dataRaceToPlace, DataRaceToPlaceDTO dataRaceToPlaceDTO) {
        if (dataRaceToPlaceDTO == null || dataRaceToPlaceDTO.getDataRaceId() == null) {
            return dataRaceToPlace;
        }

        return dataRaceToPlace.dataRace(dataRaceRepository.findOne(dataRaceToPlaceDTO.getDataRaceId()));
    }
}
