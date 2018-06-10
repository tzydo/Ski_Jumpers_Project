package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceDTO;
import org.springframework.batch.item.ItemProcessor;

public class FindRaceDataProcessorBatch implements ItemProcessor<String, DataRaceDTO> {
    private final DiagnosticMonitor diagnosticMonitor;

    public FindRaceDataProcessorBatch(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Override
    public DataRaceDTO process(String readWords) throws Exception {
        if(readWords == null || readWords.isEmpty()) {
            return null;
        }

        FindRaceDataProcessor findRaceDataProcessor = new FindRaceDataProcessor(readWords, diagnosticMonitor);
        findRaceDataProcessor.findData();
        return null;
    }
}
