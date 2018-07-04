package com.pl.skijumping.batch.datasynchronize.reader;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.ListItemReader;

public class BasicDataSynchronizeReaderBatch implements ItemStreamReader<String> {
    private final DiagnosticMonitor diagnosticMonitor;
    private final String directoryPath;
    private ListItemReader<String> listItemReader;

    public BasicDataSynchronizeReaderBatch(DiagnosticMonitor diagnosticMonitor, String directoryPath) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.directoryPath = directoryPath;
    }

    @Override
    public String read() {
        if (listItemReader == null) {
            return null;
        }

        return listItemReader.read();
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        BasicDataSynchronizeReader basicDataSynchronizeReader = new BasicDataSynchronizeReader(
                directoryPath, diagnosticMonitor);
        listItemReader = new ListItemReader<>(basicDataSynchronizeReader.synchronize());
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
//
    }

    @Override
    public void close() throws ItemStreamException {
//
    }
}
