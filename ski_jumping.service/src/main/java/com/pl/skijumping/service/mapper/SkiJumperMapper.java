package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.JumpResult;
import com.pl.skijumping.domain.entity.SkiJumper;
import com.pl.skijumping.domain.repository.JumpResultRepository;
import com.pl.skijumping.dto.SkiJumperDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = JumpResultMapper.class)
public abstract class SkiJumperMapper {
    @Autowired
    private JumpResultRepository jumpResultRepository;

    @Mapping(source = "id", target = "id")
    public abstract SkiJumperDTO toDTO(SkiJumper skiJumper);
    public abstract List<SkiJumperDTO> toDTO(List<SkiJumper> skiJumperList);

    @InheritInverseConfiguration
    public abstract SkiJumper fromDTO(SkiJumperDTO skiJumperDTO);
    public abstract List<SkiJumper> fromDTO(List<SkiJumperDTO> skiJumperList);

    @AfterMapping
    public SkiJumper setJumpResult(@MappingTarget SkiJumper skiJumper, SkiJumperDTO skiJumperDTO) {
        skiJumper.setJumpResult(jumpResultRepository.findAllByIdIn(skiJumperDTO.getJumpResultIds()));
        return skiJumper;
    }

    @AfterMapping
    public SkiJumperDTO setSkiJumperDTO(@MappingTarget SkiJumperDTO skiJumperDTO, SkiJumper skiJumper) {
        if(!skiJumper.getJumpResult().isEmpty()) {
            skiJumperDTO.setJumpResultIds(
                    skiJumper.getJumpResult().stream().map(JumpResult::getId).collect(Collectors.toList()));
        }

        return skiJumperDTO;
    }
}