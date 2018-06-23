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

@Mapper(componentModel = "spring")
public abstract class DataRaceMapper {

    @Autowired
    private CompetitionTypeService competitionTypeService;
    @Autowired
    private CompetitionNameService competitionNameService;

    @Mapping(source = "dataRace.id", target = "id")
    public abstract DataRaceDTO toDTO(DataRace dataRace);

    public abstract List<DataRaceDTO> toDTO(List<DataRace> dataRaceList);

    @InheritInverseConfiguration
    public abstract DataRace fromDTO(DataRaceDTO dataRaceDTO);

    public abstract List<DataRace> fromDTO(List<DataRaceDTO> dataRaceDTOList);

    @AfterMapping
    public DataRace setCompetitionTypeID(@MappingTarget DataRace dataRace, DataRaceDTO dataRaceDTO) {
        if (dataRaceDTO.getCompetitionName() == null || dataRaceDTO.getCompetitionType() == null) {
            return dataRace;
        }

        Optional<CompetitionTypeDTO> competitionTypeDTO = competitionTypeService
                .findByType(dataRaceDTO.getCompetitionType());

        if (!competitionTypeDTO.isPresent()) {
            dataRace.setCompetitionTypeId(null);
            return dataRace;
        }
        dataRace.setCompetitionTypeId(competitionTypeDTO.get().getId());

        Optional<CompetitionNameDTO> competitionNameDTO = competitionNameService
                .findByName(dataRaceDTO.getCompetitionName());

        if (!competitionNameDTO.isPresent()) {
            dataRace.setCompetitionNameId(null);
            return dataRace;
        }
        dataRace.setCompetitionNameId(competitionNameDTO.get().getId());
        return dataRace;
    }

    @AfterMapping
    public DataRaceDTO setCompetitionTypeID(@MappingTarget DataRaceDTO dataRaceDTO, DataRace dataRace) {
        Optional<CompetitionTypeDTO> competitionTypeDTO =
                competitionTypeService.find(dataRace.getCompetitionTypeId());

        Optional<CompetitionNameDTO> competitionNameDTO =
                competitionNameService.find(dataRace.getCompetitionNameId());

        dataRaceDTO.setCompetitionType(competitionTypeDTO.map(CompetitionTypeDTO::getType).orElse(null));
        dataRaceDTO.setCompetitionName(competitionNameDTO.map(CompetitionNameDTO::getName).orElse(null));

        return dataRaceDTO;
    }
}