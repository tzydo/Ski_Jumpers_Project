package com.pl.skijumping.batch.datareaderjob.reader.matchingword;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TournamentYearMatchingWords {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentYearMatchingWords.class);

    public Optional<List<String>> getSeasonData(String words) {
        if (words == null || words.isEmpty()) {
            LOGGER.error("Cannot find matching words from null");
            return Optional.empty();
        }
        return getMatchedWordList(words, RegexpPattern.TOURNAMENT_YEAR);
    }

    private Optional<List<String>> getMatchedWordList(String words, String regexp) {
        if(words == null || regexp == null) {
            LOGGER.error("Wrong parameters in matching words method");
            return Optional.empty();
        }
        Pattern pattern = Pattern.compile(regexp);
        ArrayList<String> matchingWordList = new ArrayList<>();
        Matcher matcher = pattern.matcher(words);

        while(matcher.find()){
            matchingWordList.add(matcher.group(1));
        }

        if (matchingWordList == null || matchingWordList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(matchingWordList);
    }
}
