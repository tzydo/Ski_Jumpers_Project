package com.pl.skijumping.service;

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

    public Optional<JumpCategoryDTO> findByShortName(String shortName) {
        if(shortName == null || shortName.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(jumpCategoryMapper.toDTO(jumpCategoryRepository.findByShortName(shortName)));
    }
}
