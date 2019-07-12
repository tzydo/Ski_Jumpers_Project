package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.CompetitionType;
import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.domain.repository.DataRaceRepository;
import com.pl.skijumping.dto.CompetitionTypeDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CompetitionTypeMapper {

    @Autowired
    private DataRaceRepository dataRaceRepository;

    @Mapping(source = "type", target = "type")
    public abstract CompetitionType fromDTO(CompetitionTypeDTO competitionTypeDTO);

    public abstract List<CompetitionType> fromDTO(List<CompetitionTypeDTO> competitionTypeDTO);

    @InheritInverseConfiguration
    public abstract CompetitionTypeDTO toDTO(CompetitionType competitionType);

    public abstract List<CompetitionTypeDTO> toDTO(List<CompetitionType> competitionType);

    @AfterMapping
    public CompetitionType setDataRace(@MappingTarget CompetitionType competitionType,
                                       CompetitionTypeDTO competitionTypeDTO) {
        if (competitionTypeDTO != null && competitionTypeDTO.getDataRaceId() != null) {
            competitionType.setDataRaceList(dataRaceRepository.findAllByIdIn(competitionTypeDTO.getDataRaceId()));
        }
        return competitionType;
    }

    @AfterMapping
    public CompetitionTypeDTO setDataRaceDTO(@MappingTarget CompetitionTypeDTO competitionTypeDTO,
                                             CompetitionType competitionType) {
        if (competitionType != null && competitionType.getDataRaceList() != null) {
            List<Long> dataRaceIds = competitionType.getDataRaceList()
                    .stream()
                    .map(DataRace::getId)
                    .collect(Collectors.toList());

            competitionTypeDTO.setDataRaceId(dataRaceIds);
        }
        return competitionTypeDTO;
    }
}

