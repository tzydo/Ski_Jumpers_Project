package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.reader;

import com.pl.skijumping.batch.datareaderjob.reader.DataReader;
import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;
import java.util.Optional;

public class DataReaderBatch implements ItemStreamReader<String> {
    private final String filePath;
    private final DiagnosticMonitor diagnosticMonitor;
    private ListItemReader<String> listItemReader;

    public DataReaderBatch(String filePath, DiagnosticMonitor diagnosticMonitor) {
        this.filePath = filePath;
        this.diagnosticMonitor = diagnosticMonitor;
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
        DataReader dataReader = new DataReader(filePath, diagnosticMonitor);
        String fileContent = dataReader.read();
        if (fileContent == null || fileContent.isEmpty()) {
            diagnosticMonitor.logError("Cannot read race data from file", getClass());
            return;
        }
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Optional<List<String>> raceDataFirstStep = matchingWords.getRaceDataFirstStep(fileContent);
        if (!raceDataFirstStep.isPresent()) {
            diagnosticMonitor.logError("Not found matching words for first step data race job", getClass());
            return;
        }
        listItemReader = new ListItemReader<>(raceDataFirstStep.get());
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        // not necessary
    }

    @Override
    public void close() throws ItemStreamException {
        // not necessary
    }
}
