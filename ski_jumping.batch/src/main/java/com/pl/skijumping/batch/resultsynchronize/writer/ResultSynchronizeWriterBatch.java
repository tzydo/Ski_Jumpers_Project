package com.pl.skijumping.batch.resultsynchronize.writer;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.domain.entity.SkiJumper;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ResultSynchronizeWriterBatch implements ItemWriter<SkiJumper> {
    private final DiagnosticMonitor diagnosticMonitor;

    public ResultSynchronizeWriterBatch(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Override
    public void write(List<? extends SkiJumper> list) throws Exception {
        if(list == null ||list.isEmpty()) return;
    // todo rozbicie encji na 2. osobno glowne dane o skoczku, osobno wyniki

    }
}
