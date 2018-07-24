package com.pl.skijumping.batch.resultsynchronize.processor;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.SkiJumperDTO;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class ResultSynchronizeProcessorBatch implements ItemProcessor<String, List<SkiJumperDTO>> {
    private final DiagnosticMonitor diagnosticMonitor;

    public ResultSynchronizeProcessorBatch(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Override
    public List<SkiJumperDTO> process(String words) {
        ResultDataSynchronize resultDataSynchronize = new ResultDataSynchronize(diagnosticMonitor);
        return resultDataSynchronize.transformData(words);
    }
}
