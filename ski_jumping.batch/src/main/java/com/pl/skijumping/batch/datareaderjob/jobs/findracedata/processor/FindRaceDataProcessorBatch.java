package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceDTO;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class FindRaceDataProcessorBatch implements ItemProcessor<String, List<DataRaceDTO>> {
    private final DiagnosticMonitor diagnosticMonitor;

    public FindRaceDataProcessorBatch(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Override
    public List<DataRaceDTO> process(String readWords) throws Exception {
        if(readWords == null || readWords.isEmpty()) {
            return null;
        }
        // can return more than one value
        FindRaceDataProcessor findRaceDataProcessor = new FindRaceDataProcessor(readWords, diagnosticMonitor);
        return findRaceDataProcessor.findData();
    }
}
