package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.reader;

import com.pl.skijumping.batch.datareaderjob.reader.DataReaderBatch;
import com.pl.skijumping.batch.datareaderjob.reader.matchingword.MatchingWords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;
import java.util.Optional;

public class FindRaceDataReaderBatch implements ItemStreamReader<String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindRaceDataReaderBatch.class);
    private final String filePath;
    private ListItemReader<String> listItemReader;

    public FindRaceDataReaderBatch(String filePath) {
        this.filePath = filePath;
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
            LOGGER.error("Cannot read race data from file");
            return;
        }
        MatchingWords matchingWords = new MatchingWords();
        Optional<List<String>> raceDataFirstStep = matchingWords.getRaceDataFirstStep(fileContent);
        if (!raceDataFirstStep.isPresent()) {
            LOGGER.error("Not found matching words for first step data race job");
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
