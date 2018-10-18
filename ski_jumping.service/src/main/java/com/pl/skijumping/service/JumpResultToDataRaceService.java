package com.pl.skijumping.service;

import com.pl.skijumping.domain.entity.JumpResultToDataRace;
import com.pl.skijumping.domain.repository.JumpResultToDataRaceRepository;
import com.pl.skijumping.dto.JumpResultToDataRaceDTO;
import com.pl.skijumping.service.mapper.JumpResultToDataRaceMapper;
import org.springframework.stereotype.Service;

@Service
public class JumpResultToDataRaceService {

    private JumpResultToDataRaceRepository jumpResultToDataRaceRepository;
    private JumpResultToDataRaceMapper jumpResultToDataRaceMapper;

    public JumpResultToDataRaceService(JumpResultToDataRaceRepository jumpResultToDataRaceRepository, JumpResultToDataRaceMapper jumpResultToDataRaceMapper) {
        this.jumpResultToDataRaceRepository = jumpResultToDataRaceRepository;
        this.jumpResultToDataRaceMapper = jumpResultToDataRaceMapper;
    }

    public JumpResultToDataRaceDTO save(JumpResultToDataRaceDTO jumpResultToDataRaceDTO) {
        if(jumpResultToDataRaceDTO == null) {
            return null;
        }

        JumpResultToDataRace jumpResultToDataRace = jumpResultToDataRaceRepository.save(jumpResultToDataRaceMapper.fromDTO(jumpResultToDataRaceDTO));
        return jumpResultToDataRaceMapper.toDTO(jumpResultToDataRace);
    }
}
