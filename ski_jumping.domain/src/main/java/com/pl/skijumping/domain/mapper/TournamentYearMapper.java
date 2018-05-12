package com.pl.skijumping.domain.mapper;

import com.pl.skijumping.domain.dto.TournamentYearDTO;
import com.pl.skijumping.domain.entity.TournamentYear;
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