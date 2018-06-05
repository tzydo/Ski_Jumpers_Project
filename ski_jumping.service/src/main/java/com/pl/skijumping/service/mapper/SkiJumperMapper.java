package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.SkiJumper;
import com.pl.skijumping.dto.SkiJumperDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkiJumperMapper {

    @Mapping(source = "skiJumper.id", target = "id")
    SkiJumperDTO toDTO(SkiJumper skiJumper);

    List<SkiJumperDTO> toDTO(List<SkiJumper> skiJumperList);

    @InheritInverseConfiguration
    SkiJumper fromDTO(SkiJumperDTO skiJumperDTO);

    List<SkiJumper> fromDTO(List<SkiJumperDTO> skiJumperList);
}