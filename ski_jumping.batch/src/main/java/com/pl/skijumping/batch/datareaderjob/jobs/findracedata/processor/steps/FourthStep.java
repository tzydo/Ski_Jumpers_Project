package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor.steps;

import com.pl.skijumping.batch.datareaderjob.reader.matchingword.MatchingWords;
import com.pl.skijumping.domain.dto.DataRaceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class FourthStep {
    private static final Logger LOGGER = LoggerFactory.getLogger(FourthStep.class);
    private final String words;

    public FourthStep(String readWords) {
        this.words = readWords;
    }

    public void setValue(DataRaceDTO dataRaceDTO) {
        MatchingWords matchingWords = new MatchingWords();
        Optional<List<String>> matchingWordsList = matchingWords.getRaceDataFourthStep(words);
        if(!matchingWordsList.isPresent()) {
            LOGGER.error(String.format("Not found any matching words for step four, in words: %s", words));
            return;
        }

        dataRaceDTO.setCompetitionName(getValue(matchingWordsList.get(), 0));
        dataRaceDTO.setCompetitionType(getValue(matchingWordsList.get(), 2));
    }

    private String getValue(List<String> words, int index) {
        if(words == null || words.isEmpty() || words.size() <index) {
            return null;
        }

        return words.get(index);
    }
}
