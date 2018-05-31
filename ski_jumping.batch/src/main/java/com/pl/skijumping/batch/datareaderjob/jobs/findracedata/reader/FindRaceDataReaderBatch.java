package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.reader;

import com.pl.skijumping.batch.datareaderjob.reader.DataReaderBatch;
import com.pl.skijumping.batch.datareaderjob.reader.matchingword.MatchingWords;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;
import java.util.Optional;

public class FindRaceDataReaderBatch implements ItemStreamReader<String> {
    private final String filePath;
    private final DiagnosticMonitor diagnosticMonitor;
    private ListItemReader<String> listItemReader;

    public FindRaceDataReaderBatch(String filePath, DiagnosticMonitor diagnosticMonitor) {
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
        DataReaderBatch dataReaderBatch = new DataReaderBatch(filePath);
        String fileContent = dataReaderBatch.read();
        if (fileContent == null || fileContent.isEmpty()) {
            diagnosticMonitor.logError("Cannot read race data from file", getClass());
            return;
        }
        MatchingWords matchingWords = new MatchingWords();
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
