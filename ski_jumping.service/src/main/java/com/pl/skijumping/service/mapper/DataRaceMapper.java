package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.service.CompetitionTypeService;
import com.pl.skijumping.dto.CompetitionTypeDTO;
import com.pl.skijumping.dto.DataRaceDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class DataRaceMapper {

    @Autowired
    private CompetitionTypeService competitionTypeService;

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
                .findByTypeAndName(dataRaceDTO.getCompetitionType(), dataRaceDTO.getCompetitionName());

        if (!competitionTypeDTO.isPresent()) {
            dataRace.setCompetitionTypeId(null);
            return dataRace;
        }
        dataRace.setCompetitionTypeId(competitionTypeDTO.get().getId());
        return dataRace;
    }

    @AfterMapping
    public DataRaceDTO setCompetitionTypeID(@MappingTarget DataRaceDTO dataRaceDTO, DataRace dataRace) {
        Optional<CompetitionTypeDTO> competitionTypeDTO =
                competitionTypeService.find(dataRace.getCompetitionTypeId());

        if (competitionTypeDTO.isPresent()) {
            dataRaceDTO.setCompetitionType(competitionTypeDTO.get().getCompetitionType());
            dataRaceDTO.setCompetitionName(competitionTypeDTO.get().getCompetitionName());
            return dataRaceDTO;
        }
        dataRaceDTO.setCompetitionType(null);
        dataRaceDTO.setCompetitionName(null);

        return dataRaceDTO;
    }
}