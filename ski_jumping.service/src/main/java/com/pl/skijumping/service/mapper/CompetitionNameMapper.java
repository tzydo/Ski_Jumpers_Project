package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.CompetitionName;
import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.domain.repository.DataRaceRepository;
import com.pl.skijumping.dto.CompetitionNameDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CompetitionNameMapper {

    @Autowired
    private DataRaceRepository dataRaceRepository;

    @Mapping(source = "name", target = "name")
    public abstract CompetitionName fromDTO(CompetitionNameDTO competitionNameDTO);

    public abstract List<CompetitionName> fromDTO(List<CompetitionNameDTO> competitionNameDTO);

    @InheritInverseConfiguration
    public abstract CompetitionNameDTO toDTO(CompetitionName competitionName);

    public abstract List<CompetitionNameDTO> toDTO(List<CompetitionName> competitionName);

    @AfterMapping
    public CompetitionName setCompetitionName(@MappingTarget CompetitionName competitionName,
                                              CompetitionNameDTO competitionNameDTO) {
        if (competitionNameDTO != null && competitionNameDTO.getDataRaceId() != null) {
            competitionName.setDataRaceList(dataRaceRepository.findAllByIdIn(competitionNameDTO.getDataRaceId()));
        }
        return competitionName;
    }

    @AfterMapping
    public CompetitionNameDTO setCompetitionNameDTO(@MappingTarget CompetitionNameDTO competitionNameDTO,
                                                    CompetitionName competitionName) {
        if (competitionName != null && competitionName.getDataRaceList() != null) {
            List<Long> dataRaceIds = competitionName.getDataRaceList()
                    .stream()
                    .map(DataRace::getId)
                    .collect(Collectors.toList());

            competitionNameDTO.setDataRaceId(dataRaceIds);
        }
        return competitionNameDTO;
    }
}
