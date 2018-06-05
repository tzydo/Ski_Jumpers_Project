package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.TournamentYear;
import com.pl.skijumping.dto.TournamentYearDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TournamentYearMapper {

    @Mapping(target = "id", source = "id")
    TournamentYearDTO toDTO(TournamentYear tournamentYear);
    List<TournamentYearDTO> toDTO(List<TournamentYear> tournamentYear);

    @InheritInverseConfiguration
    TournamentYear fromDTO(TournamentYearDTO tournamentYear);
    List<TournamentYear> fromDTO(List<TournamentYearDTO> tournamentYear);
}