package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.JumpCategory;
import com.pl.skijumping.dto.JumpCategoryDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class JumpCategoryMapper {
    public abstract JumpCategoryDTO toDTO(JumpCategory jumpCategory);

    public abstract List<JumpCategoryDTO> toDTO(List<JumpCategory> jumpCategoryDTOS);

    @InheritInverseConfiguration
    public abstract JumpCategory fromDTO(JumpCategoryDTO jumpCategoryDTO);

    public abstract List<JumpCategory> fromDTO(List<JumpCategoryDTO> jumpCategoryDTOS);
}