package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.ImportedFile;
import com.pl.skijumping.dto.ImportedFileDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImportedFileMapper {

    ImportedFileMapper IMPORTED_FILE_MAPPER = Mappers.getMapper(ImportedFileMapper.class);

    @Mapping(source = "id", target = "id")
    ImportedFileDTO toDTO(ImportedFile importedFile);

    List<ImportedFileDTO> toDTO(List<ImportedFile> importedFile);

    @InheritInverseConfiguration
    ImportedFile fromDTO(ImportedFileDTO importedFileDTO);

    List<ImportedFile> fromDTO(List<ImportedFileDTO> importedFileDTO);
}
