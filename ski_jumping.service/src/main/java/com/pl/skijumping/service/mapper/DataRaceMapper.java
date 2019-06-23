package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.domain.model.Gender;
import com.pl.skijumping.dto.CompetitionTypeDTO;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.dto.JumpCategoryDTO;
import com.pl.skijumping.service.CompetitionTypeService;
import com.pl.skijumping.service.JumpCategoryService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = {CompetitionTypeMapper.class})
public abstract class DataRaceMapper {

    @Autowired
    private CompetitionTypeService competitionTypeService;
    @Autowired
    private CompetitionTypeMapper competitionTypeMapper;
    @Autowired
    private JumpCategoryService jumpCategoryService;
    @Autowired
    private JumpCategoryMapper jumpCategoryMapper;

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "competitionType.type", target = "competitionType"),
            @Mapping(source = "gender", target = "gender", ignore = true),
            @Mapping(source = "cancelled", target = "isCancelled")
    })
    public abstract DataRaceDTO toDTO(DataRace dataRace);

    public abstract List<DataRaceDTO> toDTO(List<DataRace> dataRaceList);

    @InheritInverseConfiguration
    public abstract DataRace fromDTO(DataRaceDTO dataRaceDTO);

    public abstract List<DataRace> fromDTO(List<DataRaceDTO> dataRaceDTOList);

    @AfterMapping
    void setGender(@MappingTarget DataRace dataRace, DataRaceDTO dataRaceDTO) {
        if(dataRace == null) {
            return;
        }
        dataRace.setGender(Gender.getValue(dataRaceDTO.getGender()));
        Optional<CompetitionTypeDTO> competitionTypeDTO = competitionTypeService
                .findByType(dataRaceDTO.getCompetitionType());

        dataRace.setCompetitionType(competitionTypeDTO.map(
                competitionTypeDTO1 -> competitionTypeMapper.fromDTO(competitionTypeDTO1)).orElse(null));

        if(dataRaceDTO.getJumpCategoryId() == null) {
            return;
        }
        Optional<JumpCategoryDTO> jumpCategoryDTO = jumpCategoryService.findById(dataRaceDTO.getJumpCategoryId());
        jumpCategoryDTO.ifPresent(jumpCategoryDTO1 -> dataRace.setJumpCategory(jumpCategoryMapper.fromDTO(jumpCategoryDTO1)));
    }

    @AfterMapping
    void setJumpCategory(@MappingTarget DataRaceDTO dataRaceDTO, DataRace dataRace) {
        if(dataRace == null) {
            return;
        }
        dataRaceDTO.setGender(dataRace.getGender() != null ? dataRace.getGender().name() : null);
        dataRaceDTO.setCompetitionType(dataRace.getCompetitionType() != null && dataRace.getCompetitionType().getType() != null? dataRace.getCompetitionType().getType() : null);
        dataRaceDTO.setJumpCategoryId(dataRace.getJumpCategory() != null && dataRace.getJumpCategory().getId() != null? dataRace.getJumpCategory().getId() : null);
    }
}