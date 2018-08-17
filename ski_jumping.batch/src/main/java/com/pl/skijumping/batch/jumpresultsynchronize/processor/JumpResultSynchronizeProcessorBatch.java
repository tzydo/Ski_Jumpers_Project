package com.pl.skijumping.batch.jumpresultsynchronize.processor;

import com.pl.skijumping.common.util.Pair;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.service.SkiJumperService;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class JumpResultSynchronizeProcessorBatch implements ItemProcessor<Pair<Long, String>, List<JumpResultDTO>> {
    private final DiagnosticMonitor diagnosticMonitor;
    private final SkiJumperService skiJumperService;

    public JumpResultSynchronizeProcessorBatch(DiagnosticMonitor diagnosticMonitor, SkiJumperService skiJumperService) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.skiJumperService = skiJumperService;
    }

    @Override
    public List<JumpResultDTO> process(Pair<Long, String> raceDataIdAndWords) {
        JumpResultDataSynchronize jumpResultDataSynchronize = new JumpResultDataSynchronize(diagnosticMonitor, skiJumperService, raceDataIdAndWords.getLeft());
        return jumpResultDataSynchronize.transformData(raceDataIdAndWords.getRight());
    }
}
