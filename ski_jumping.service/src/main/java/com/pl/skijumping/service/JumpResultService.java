package com.pl.skijumping.service;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.domain.entity.JumpResult;
import com.pl.skijumping.domain.repository.JumpResultRepository;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.service.mapper.JumpResultMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JumpResultService {
    private final JumpResultRepository jumpResultRepository;
    private final JumpResultMapper jumpResultMapper;
    private final DiagnosticMonitor diagnosticMonitor;

    public JumpResultService(JumpResultRepository jumpResultRepository, JumpResultMapper jumpResultMapper, DiagnosticMonitor diagnosticMonitor) {
        this.jumpResultRepository = jumpResultRepository;
        this.jumpResultMapper = jumpResultMapper;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    public JumpResultDTO save(JumpResultDTO jumpResultDTO) {
        if (jumpResultDTO == null) {
            diagnosticMonitor.logWarn("Cannot save null jumpResult");
            return jumpResultDTO;
        }
        JumpResult savedJumpResult = jumpResultRepository.save(jumpResultMapper.fromDTO(jumpResultDTO));
        return jumpResultMapper.toDTO(savedJumpResult);
    }


    public JumpResultDTO update(JumpResultDTO jumpResultDTO) {
        if (jumpResultDTO == null) {
            diagnosticMonitor.logWarn("Cannot update null jumpResult");
            return jumpResultDTO;
        }

        JumpResult existingJumpResult = jumpResultRepository.findByDataRace_IdAndSkiJumper_IdAndRank(jumpResultDTO.getDataRaceId(), jumpResultDTO.getJumperId(), jumpResultDTO.getRank());

        if(existingJumpResult == null) {
            return this.save(jumpResultDTO);
        }

        existingJumpResult.setFirstJump(jumpResultDTO.getFirstJump());
        existingJumpResult.setPointsForFirstJump(jumpResultDTO.getPointsForFirstJump());
        existingJumpResult.setSecondJump(jumpResultDTO.getSecondJump());
        existingJumpResult.setPointsForSecondJump(jumpResultDTO.getPointsForSecondJump());
        existingJumpResult.setTotalPoints(jumpResultDTO.getTotalPoints());

        return jumpResultMapper.toDTO(jumpResultRepository.save(existingJumpResult));
    }


    public List<JumpResultDTO> findAll() {
        List<JumpResult> jumpResults = jumpResultRepository.findAll();
        if (jumpResults.isEmpty()) {
            return new ArrayList<>();
        }

        return jumpResultMapper.toDTO(jumpResults);
    }
}
