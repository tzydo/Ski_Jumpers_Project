package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.domain.entity.JumpResult;
import com.pl.skijumping.domain.entity.JumpResultToDataRace;
import com.pl.skijumping.domain.repository.DataRaceRepository;
import com.pl.skijumping.domain.repository.JumpResultRepository;
import com.pl.skijumping.dto.JumpResultToDataRaceDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class JumpResultToDataRaceMapper {

    @Autowired
    private JumpResultRepository jumpResultRepository;
    @Autowired
    private DataRaceRepository dataRaceRepository;

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "jumpResultId", source = "jumpResult.id"),
        @Mapping(target = "dataRaceId", source = "dataRace.id"),

    })
    public abstract JumpResultToDataRaceDTO toDTO(JumpResultToDataRace jumpResultToDataRace);
    public abstract List<JumpResultToDataRaceDTO> toDTO(List<JumpResultToDataRace> jumpResultToDataRace);

    @InheritInverseConfiguration
    public abstract JumpResultToDataRace fromDTO(JumpResultToDataRaceDTO jumpResultToDataRaceDTO);
    public abstract List<JumpResultToDataRace> fromDTO(List<JumpResultToDataRaceDTO> jumpResultToDataRaceDTOs);

    @AfterMapping
    public JumpResultToDataRace setJumpResultToDataRace(@MappingTarget JumpResultToDataRace jumpResultToDataRace,
                                                         JumpResultToDataRaceDTO jumpResultToDataRaceDTO) {
        if(jumpResultToDataRaceDTO.getJumpResultId() != null) {
            JumpResult jumpResult = jumpResultRepository.findOne(jumpResultToDataRaceDTO.getJumpResultId());
            jumpResultToDataRace.setJumpResult(jumpResult);
        }

        if(jumpResultToDataRaceDTO.getDataRaceId() != null) {
            DataRace dataRace = dataRaceRepository.findOne(jumpResultToDataRaceDTO.getDataRaceId());
            jumpResultToDataRace.setDataRace(dataRace);
        }
        return jumpResultToDataRace;
    }
}
