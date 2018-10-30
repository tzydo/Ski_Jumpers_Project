package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.SkiJumper;
import com.pl.skijumping.dto.SkiJumperDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = JumpResultMapper.class)
public abstract class SkiJumperMapper {
    @Mapping(source = "id", target = "id")
    public abstract SkiJumperDTO toDTO(SkiJumper skiJumper);
    public abstract List<SkiJumperDTO> toDTO(List<SkiJumper> skiJumperList);

    @InheritInverseConfiguration
    public abstract SkiJumper fromDTO(SkiJumperDTO skiJumperDTO);
    public abstract List<SkiJumper> fromDTO(List<SkiJumperDTO> skiJumperList);
}