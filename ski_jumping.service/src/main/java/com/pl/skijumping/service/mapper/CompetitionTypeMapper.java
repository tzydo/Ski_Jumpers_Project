package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.CompetitionType;
import com.pl.skijumping.dto.CompetitionTypeDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompetitionTypeMapper {
    @Mapping(source = "type", target = "competitionType")
    CompetitionType fromDTO(CompetitionTypeDTO competitionTypeDTO);
    List<CompetitionType> fromDTO(List<CompetitionTypeDTO> competitionTypeDTO);

    @InheritInverseConfiguration
    CompetitionTypeDTO toDTO(CompetitionType competitionType);
    List<CompetitionTypeDTO> toDTO(List<CompetitionType> competitionType);
}
