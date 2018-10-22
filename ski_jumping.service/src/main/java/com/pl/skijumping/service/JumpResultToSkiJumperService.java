package com.pl.skijumping.service;

import com.pl.skijumping.domain.entity.JumpResultToSkiJumper;
import com.pl.skijumping.domain.repository.JumpResultToSkiJumperRepository;
import com.pl.skijumping.dto.JumpResultToSkiJumperDTO;
import com.pl.skijumping.service.mapper.JumpResultToSkiJumperMapper;
import org.springframework.stereotype.Service;

@Service
public class JumpResultToSkiJumperService {

    private JumpResultToSkiJumperRepository jumpResultToSkiJumperRepository;
    private JumpResultToSkiJumperMapper jumpResultToSkiJumperMapper;

    public JumpResultToSkiJumperService(JumpResultToSkiJumperRepository jumpResultToSkiJumperRepository,
                                        JumpResultToSkiJumperMapper jumpResultToSkiJumperMapper) {
        this.jumpResultToSkiJumperRepository = jumpResultToSkiJumperRepository;
        this.jumpResultToSkiJumperMapper = jumpResultToSkiJumperMapper;
    }

    public JumpResultToSkiJumperDTO save(JumpResultToSkiJumperDTO jumpResultToSkiJumperDTO) {
        if (jumpResultToSkiJumperDTO == null) {
            return null;
        }

        JumpResultToSkiJumper jumpResultToSkiJumper = jumpResultToSkiJumperRepository.save(jumpResultToSkiJumperMapper.fromDTO(jumpResultToSkiJumperDTO));
        return jumpResultToSkiJumperMapper.toDTO(jumpResultToSkiJumper);
    }
}
