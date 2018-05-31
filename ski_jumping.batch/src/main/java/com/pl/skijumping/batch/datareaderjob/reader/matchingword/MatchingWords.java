package com.pl.skijumping.batch.datareaderjob.reader.matchingword;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class MatchingWords {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchingWords.class);

    public MatchingWords() {
        //
    }
    public Optional<List<String>> getTournamentYears(String words) {
        if (isEmpty(words)) return Optional.empty();
        FindMatchingWords findMatchingWords = new FindMatchingWords();
        return findMatchingWords.getSeasonData(words, RegexpPattern.TOURNAMENT_YEAR, true);
    }

    public Optional<List<String>> getRaceDataFirstStep(String words) {
        if (isEmpty(words)) return Optional.empty();
        FindMatchingWords findMatchingWords = new FindMatchingWords();
        return findMatchingWords.getSeasonData(words, RegexpPattern.FIRST_STEP_DATA_RACE, true);
    }
    public Optional<List<String>> getRaceDataSecondStep(String words) {
        if (isEmpty(words)) return Optional.empty();
        FindMatchingWords findMatchingWords = new FindMatchingWords();
        return findMatchingWords.getSeasonData(words, RegexpPattern.SECOND_STEP_DATA_RACE, false);
    }

    public Optional<List<String>> getRaceDataThirdStep(String words) {
        if (isEmpty(words)) return Optional.empty();
        FindMatchingWords findMatchingWords = new FindMatchingWords();
        return findMatchingWords.getSeasonData(words, RegexpPattern.THIRD_STEP_DATA_RACE, true);
    }

    public Optional<List<String>> getRaceDataFourthStep(String words) {
        if (isEmpty(words)) return Optional.empty();
        FindMatchingWords findMatchingWords = new FindMatchingWords();
        return findMatchingWords.getSeasonData(words, RegexpPattern.FOURTH_STEP_DATA_RACE, true);
    }

    public Optional<List<String>> getRaceDate(String words) {
        if (isEmpty(words)) return Optional.empty();
        FindMatchingWords findMatchingWords = new FindMatchingWords();
        return findMatchingWords.getSeasonData(words, RegexpPattern.SEASON_DATE, true);
    }

    private boolean isEmpty(String words) {
        if (words == null || words.isEmpty()) {
            LOGGER.error("Cannot find matching words from null");
            return true;
        }
        return false;
    }
}
