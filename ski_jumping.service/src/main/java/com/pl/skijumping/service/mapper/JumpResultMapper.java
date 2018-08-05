package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.JumpResult;
import com.pl.skijumping.domain.repository.DataRaceRepository;
import com.pl.skijumping.domain.repository.SkiJumperRepository;
import com.pl.skijumping.dto.JumpResultDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class JumpResultMapper {
    @Autowired
    private SkiJumperRepository skiJumperRepository;
    @Autowired
    private DataRaceRepository dataRaceRepository;

    @Mapping(source = "id", target = "id")
    public abstract JumpResult fromDTO(JumpResultDTO jumpResultDTO);
    public abstract List<JumpResult> fromDTO(List<JumpResultDTO> jumpResultDTOS);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "dataRaceId", source = "dataRace.id"),
            @Mapping(target = "jumperId", source = "skiJumper.id")
    })
    public abstract JumpResultDTO toDTO(JumpResult jumpResultDTO);
    public abstract List<JumpResultDTO> toDTO(List<JumpResult> jumpResultDTOS);

    @AfterMapping
    public JumpResult setJumpResult(@MappingTarget JumpResult jumpResult, JumpResultDTO jumpResultDTO) {
        jumpResult.setSkiJumper(skiJumperRepository.findOne(jumpResultDTO.getJumperId()));
        jumpResult.setDataRace(dataRaceRepository.findOne(jumpResultDTO.getDataRaceId()));
        return jumpResult;
    }

    @AfterMapping
    public JumpResultDTO setJumpResultDTO(JumpResult jumpResult, @MappingTarget JumpResultDTO jumpResultDTO) {
        jumpResultDTO.setJumperId(jumpResult.getSkiJumper().getId());
        jumpResultDTO.setDataRaceId(jumpResult.getDataRace().getId());
        return jumpResultDTO;
    }
}
