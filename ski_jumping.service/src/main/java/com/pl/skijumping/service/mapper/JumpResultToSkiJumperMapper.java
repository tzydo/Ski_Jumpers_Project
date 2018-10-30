package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.JumpResult;
import com.pl.skijumping.domain.entity.JumpResultToSkiJumper;
import com.pl.skijumping.domain.entity.SkiJumper;
import com.pl.skijumping.domain.repository.JumpResultRepository;
import com.pl.skijumping.domain.repository.SkiJumperRepository;
import com.pl.skijumping.dto.JumpResultToSkiJumperDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class JumpResultToSkiJumperMapper {
    @Autowired
    private JumpResultRepository jumpResultRepository;
    @Autowired
    private SkiJumperRepository skiJumperRepository;

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "jumpResultId", source = "jumpResult.id"),
            @Mapping(target = "skiJumperId", source = "skiJumper.id"),

    })
    public abstract JumpResultToSkiJumperDTO toDTO(JumpResultToSkiJumper jumpResultToSkiJumper);
    public abstract List<JumpResultToSkiJumperDTO> toDTO(List<JumpResultToSkiJumper> jumpResultToSkiJumper);

    @InheritInverseConfiguration
    public abstract JumpResultToSkiJumper fromDTO(JumpResultToSkiJumperDTO jumpResultToSkiJumperDTO);
    public abstract List<JumpResultToSkiJumper> fromDTO(List<JumpResultToSkiJumperDTO> jumpResultToSkiJumperDTO);

    @AfterMapping
    public JumpResultToSkiJumper setJumpResultToSkiJumper(@MappingTarget JumpResultToSkiJumper jumpResultToSkiJumper,
                                                        JumpResultToSkiJumperDTO jumpResultToSkiJumperDTO) {
        if(jumpResultToSkiJumperDTO.getJumpResultId() != null) {
            JumpResult jumpResult = jumpResultRepository.findOne(jumpResultToSkiJumperDTO.getJumpResultId());
            jumpResultToSkiJumper.setJumpResult(jumpResult);
        }

        if(jumpResultToSkiJumperDTO.getSkiJumperId() != null) {
            SkiJumper skiJumper = skiJumperRepository.findOne(jumpResultToSkiJumperDTO.getSkiJumperId());
            jumpResultToSkiJumper.setSkiJumper(skiJumper);
        }
        return jumpResultToSkiJumper;
    }
}
