package com.pl.skijumping.service;

import com.pl.skijumping.domain.entity.ImportedFile;
import com.pl.skijumping.domain.repository.ImportedFileRepository;
import com.pl.skijumping.dto.ImportedFileDTO;
import com.pl.skijumping.service.mapper.ImportedFileMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImportedFileService {
    private final ImportedFileRepository importedFileRepository;
    private final ImportedFileMapper importedFileMapper;

    public ImportedFileService(ImportedFileRepository importedFileRepository,
                               ImportedFileMapper importedFileMapper) {
        this.importedFileRepository = importedFileRepository;
        this.importedFileMapper = importedFileMapper;
    }

    public List<ImportedFileDTO> findAll() {
        return importedFileMapper.toDTO(importedFileRepository.findAll());
    }

    public Map<String, String> findAllInMap() {
        List<ImportedFile> importedFiles = importedFileRepository.findAll();
        if (importedFiles == null || importedFiles.isEmpty()) {
            return new HashMap<>();
        }

        return importedFiles.stream()
                .collect(Collectors.toMap(ImportedFile::getFile, ImportedFile::getUrl));
    }

    public ImportedFileDTO save(ImportedFileDTO importedFileDTO) {
        if (importedFileDTO == null) {
            return null;
        }

        ImportedFile importedFile = importedFileRepository.save(importedFileMapper.fromDTO(importedFileDTO));
        return importedFileMapper.toDTO(importedFile);
    }
}
