package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.JumpResult;
import com.pl.skijumping.dto.JumpResultDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class JumpResultMapper {
    public abstract JumpResultDTO toDTO(JumpResult jumpResultDTO);

    public abstract List<JumpResultDTO> toDTO(List<JumpResult> jumpResultDTOS);

    @InheritInverseConfiguration
    public abstract JumpResult fromDTO(JumpResultDTO jumpResultDTO);

    public abstract List<JumpResult> fromDTO(List<JumpResultDTO> jumpResultDTOS);
}
