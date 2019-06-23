package com.pl.skijumping.service;

import com.pl.skijumping.domain.entity.JumpCategory;
import com.pl.skijumping.domain.repository.JumpCategoryRepository;
import com.pl.skijumping.dto.JumpCategoryDTO;
import com.pl.skijumping.service.mapper.JumpCategoryMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JumpCategoryService {
    private final JumpCategoryRepository jumpCategoryRepository;
    private final JumpCategoryMapper jumpCategoryMapper;

    public JumpCategoryService(JumpCategoryRepository jumpCategoryRepository,
                               JumpCategoryMapper jumpCategoryMapper) {
        this.jumpCategoryRepository = jumpCategoryRepository;
        this.jumpCategoryMapper = jumpCategoryMapper;
    }

    public JumpCategoryDTO save(JumpCategoryDTO jumpCategoryDTO) {
        if(jumpCategoryDTO == null) {
            return null;
        }

        return jumpCategoryMapper.toDTO(jumpCategoryRepository.save(jumpCategoryMapper.fromDTO(jumpCategoryDTO)));
    }

    public Optional<JumpCategoryDTO> findByShortName(String shortName) {
        if(shortName == null || shortName.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(jumpCategoryMapper.toDTO(jumpCategoryRepository.findByShortName(shortName)));
    }

    public Optional<JumpCategoryDTO> findById(Integer id) {
        JumpCategory jumpCategory = jumpCategoryRepository.findById(id);
        return Optional.ofNullable(jumpCategoryMapper.toDTO(jumpCategory));
    }
}
