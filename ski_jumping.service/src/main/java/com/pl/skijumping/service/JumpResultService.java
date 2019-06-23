package com.pl.skijumping.service;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.domain.entity.JumpResult;
import com.pl.skijumping.domain.entity.QJumpResult;
import com.pl.skijumping.domain.repository.JumpResultRepository;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.service.mapper.JumpResultMapper;
import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
            return null;
        }
        JumpResult savedJumpResult = jumpResultRepository.save(jumpResultMapper.fromDTO(jumpResultDTO));
        return jumpResultMapper.toDTO(savedJumpResult);
    }
}
