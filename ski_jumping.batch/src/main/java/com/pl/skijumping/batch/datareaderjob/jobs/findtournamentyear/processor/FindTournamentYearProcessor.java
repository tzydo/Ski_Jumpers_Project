package com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.processor;

import com.pl.skijumping.batch.datareaderjob.reader.matchingword.MatchingWords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindTournamentYearProcessor implements ItemProcessor<String, List<String>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindTournamentYearProcessor.class);

    @Override
    public List<String> process(String fileContent) throws Exception {
        LOGGER.info("Searching for matches tournament years");
        if (fileContent == null || fileContent.isEmpty()) {
            LOGGER.error("Cannot find matching words, file is empty");
            return new ArrayList<>();
        }
        MatchingWords matchingWords = new MatchingWords();
        Optional<List<String>> tournamentYears = matchingWords.getTournamentYears(fileContent);
        if (!tournamentYears.isPresent() || tournamentYears.get().isEmpty()) {
            LOGGER.error("Not found any matching words.");
            return new ArrayList<>();
        }

        LOGGER.info(String.format("Found %d matching tournament years", tournamentYears.get().size()));
        return tournamentYears.get();
    }
}
