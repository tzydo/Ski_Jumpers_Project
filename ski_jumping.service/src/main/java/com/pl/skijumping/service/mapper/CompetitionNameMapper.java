package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.CompetitionName;
import com.pl.skijumping.dto.CompetitionNameDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompetitionNameMapper {
    @Mapping(source = "name", target = "competitionName")
    CompetitionName fromDTO(CompetitionNameDTO competitionNameDTO);
    List<CompetitionName> fromDTO(List<CompetitionNameDTO> competitionNameDTO);

    @InheritInverseConfiguration
    CompetitionNameDTO toDTO(CompetitionName competitionName);
    List<CompetitionNameDTO> toDTO(List<CompetitionName> competitionName);
}
