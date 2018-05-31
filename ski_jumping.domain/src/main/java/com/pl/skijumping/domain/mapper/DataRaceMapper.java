//package com.pl.skijumping.domain.mapper;
//
//import com.pl.skijumping.domain.dto.DataRaceDTO;
//import com.pl.skijumping.domain.entity.DataRace;
//import org.mapstruct.*;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public abstract class DataRaceMapper {
//
//    @Autowired
//    private final CompetitionTypeSer
//
//    @Mapping(source = "dataRace.id", target = "id")
//    DataRaceDTO toDTO(DataRace dataRace);
//
//    List<DataRaceDTO> toDTO(List<DataRace> dataRaceList);
//
//    @InheritInverseConfiguration
//    DataRace fromDTO(DataRaceDTO dataRaceDTO);
//
//    List<DataRace> fromDTO(List<DataRaceDTO> dataRaceDTOList);
//
//    @AfterMapping
//    public default void setCompetitionType(@MappingTarget DataRace dataRace) {
//
//    }
//}