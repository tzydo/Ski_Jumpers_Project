package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.dto.CompetitionNameDTO;
import com.pl.skijumping.dto.CompetitionTypeDTO;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.service.CompetitionNameService;
import com.pl.skijumping.service.CompetitionTypeService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = {CompetitionTypeMapper.class, CompetitionNameMapper.class} )
public abstract class DataRaceMapper {

    @Autowired
    private CompetitionTypeService competitionTypeService;
    @Autowired
    private CompetitionNameService competitionNameService;
    @Autowired
    private CompetitionTypeMapper competitionTypeMapper;
    @Autowired
    private CompetitionNameMapper competitionNameMapper;

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "competitionType.type", target = "competitionType"),
            @Mapping(source = "competitionName.name", target = "competitionName")
    })
    public abstract DataRaceDTO toDTO(DataRace dataRace);
    public abstract List<DataRaceDTO> toDTO(List<DataRace> dataRaceList);

    @InheritInverseConfiguration
    public abstract DataRace fromDTO(DataRaceDTO dataRaceDTO);
    public abstract List<DataRace> fromDTO(List<DataRaceDTO> dataRaceDTOList);

    @AfterMapping
    public DataRace setCompetitionType(@MappingTarget DataRace dataRace, DataRaceDTO dataRaceDTO) {
        Optional<CompetitionTypeDTO> competitionTypeDTO = competitionTypeService
                .findByType(dataRaceDTO.getCompetitionType());

        dataRace.setCompetitionType(competitionTypeDTO.map(
                competitionTypeDTO1 -> competitionTypeMapper.fromDTO(competitionTypeDTO1)).orElse(null));

        Optional<CompetitionNameDTO> competitionNameDTO = competitionNameService
                .findByName(dataRaceDTO.getCompetitionName());

        dataRace.setCompetitionName(competitionNameDTO.map(
                competitionNameDTO1 -> competitionNameMapper.fromDTO(competitionNameDTO1)).orElse(null));

        return dataRace;
    }
}