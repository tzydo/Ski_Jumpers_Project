package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.JumpResult;
import com.pl.skijumping.domain.repository.JumpResultToDataRaceRepository;
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
    private JumpResultToDataRaceRepository jumpResultToDataRaceRepository;

    public abstract JumpResultDTO toDTO(JumpResult jumpResultDTO);
    public abstract List<JumpResultDTO> toDTO(List<JumpResult> jumpResultDTOS);

    @InheritInverseConfiguration
    public abstract JumpResult fromDTO(JumpResultDTO jumpResultDTO);
    public abstract List<JumpResult> fromDTO(List<JumpResultDTO> jumpResultDTOS);


    @AfterMapping
    public JumpResult setJumpResult(@MappingTarget JumpResult jumpResult, JumpResultDTO jumpResultDTO) {
        if (jumpResultDTO.getJumperId() != null) {
            jumpResult.setSkiJumper(skiJumperRepository.findOne(jumpResultDTO.getJumperId()));
        }
        if (jumpResultDTO.getJumpResultToDataRaceId() != null) {
            jumpResult.setJumpResultToDataRace(
                    jumpResultToDataRaceRepository.findOne(jumpResultDTO.getJumpResultToDataRaceId()));
        }
        return jumpResult;
    }

    @AfterMapping
    public JumpResultDTO setJumpResultDTO(JumpResult jumpResult, @MappingTarget JumpResultDTO jumpResultDTO) {
        if (jumpResult.getSkiJumper() != null) {
            jumpResultDTO.setJumperId(jumpResult.getSkiJumper().getId());
        }

        if (jumpResult.getJumpResultToDataRace() != null) {
            jumpResultDTO.setJumpResultToDataRaceId(jumpResult.getJumpResultToDataRace().getId());
        }
        return jumpResultDTO;
    }
}
