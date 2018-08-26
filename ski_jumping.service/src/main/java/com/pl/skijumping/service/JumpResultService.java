package com.pl.skijumping.service;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.domain.entity.JumpResult;
import com.pl.skijumping.domain.repository.JumpResultRepository;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.service.mapper.JumpResultMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<JumpResultDTO> findByParams(Long dataRaceId, Long skiJumperId, Integer rank) {
        JumpResult jumpResult = jumpResultRepository.findByDataRace_IdAndSkiJumper_IdAndRank(dataRaceId, skiJumperId, rank);
        return Optional.ofNullable(jumpResultMapper.toDTO(jumpResult));
    }


    //ToDo testy!!!!!
    public JumpResultDTO update(JumpResultDTO jumpResultDTO) {
        if (jumpResultDTO == null) {
            diagnosticMonitor.logWarn("Cannot update null jumpResult");
            return null;
        }

        Optional<JumpResultDTO> existingJumpResultDTO = this.findByParams(jumpResultDTO.getDataRaceId(), jumpResultDTO.getJumperId(), jumpResultDTO.getRank());

        if (!existingJumpResultDTO.isPresent()) {
            return this.save(jumpResultDTO);
        }

        existingJumpResultDTO.get().setFirstJump(jumpResultDTO.getFirstJump());
        existingJumpResultDTO.get().setPointsForFirstJump(jumpResultDTO.getPointsForFirstJump());
        existingJumpResultDTO.get().setSecondJump(jumpResultDTO.getSecondJump());
        existingJumpResultDTO.get().setPointsForSecondJump(jumpResultDTO.getPointsForSecondJump());
        existingJumpResultDTO.get().setTotalPoints(jumpResultDTO.getTotalPoints());

        return this.save(existingJumpResultDTO.get());
    }


    public List<JumpResultDTO> findAll() {
        List<JumpResult> jumpResults = jumpResultRepository.findAll();
        if (jumpResults.isEmpty()) {
            return new ArrayList<>();
        }

        return jumpResultMapper.toDTO(jumpResults);
    }
}
