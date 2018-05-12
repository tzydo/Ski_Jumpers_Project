package com.pl.skijumping.batch.datareaderjob.reader.matchingword;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class MatchingWords {
    private final Logger LOGGER = LoggerFactory.getLogger(MatchingWords.class);

    public MatchingWords() {
        //
    }

    public Optional<List<String>> getTournamentYears(String words) {
        if (words == null || words.isEmpty()) {
            LOGGER.error("Cannot find matching words from null");
            return Optional.empty();
        }
        TournamentYearMatchingWords tournamentYearMatchingWords = new TournamentYearMatchingWords();
        return tournamentYearMatchingWords.getSeasonData(words);
    }

//    public Optional<List<DataTextDTO>> getRaceData(String words) {
//        if (words == null || words.isEmpty()) {
//            LOGGER.error("Cannot find matching words from null");
//            return Optional.empty();
//        }
//
//        return getMatchedWordList(words, RegexpPattern.RACE_DATA);
//    }


}
