package com.pl.skijumping.batch.datareaderjob.reader.matchingword;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FindMatchingWords {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindMatchingWords.class);

    public Optional<List<String>> getSeasonData(String words, String regexp, Boolean exactMatch) {
        if (words == null || words.isEmpty()) {
            LOGGER.error("Cannot find matching words from null");
            return Optional.empty();
        }

        if (exactMatch) {
            return getExactMatchWordList(words, regexp);
        }
        return getMatchWordList(words, regexp);
    }

    private Optional<List<String>> getExactMatchWordList(String words, String regexp) {
        if (words == null || regexp == null) {
            LOGGER.error("Wrong parameters in matching words method");
            return Optional.empty();
        }
        Pattern pattern = Pattern.compile(regexp);
        ArrayList<String> matchingWordList = new ArrayList<>();
        Matcher matcher = pattern.matcher(words);

        while (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                String group = matcher.group(i);
                if (!group.contains("<") && !group.contains(">")) {
                    matchingWordList.add(group);
                }
            }
        }
        if (matchingWordList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(matchingWordList);
    }

    private Optional<List<String>> getMatchWordList(String words, String regexp) {
        if (words == null || regexp == null) {
            LOGGER.error("Wrong parameters in matching words method");
            return Optional.empty();
        }
        Pattern pattern = Pattern.compile(regexp);
        ArrayList<String> matchingWordList = new ArrayList<>();
        Matcher matcher = pattern.matcher(words);

        while (matcher.find()) {
                matchingWordList.add(matcher.group(0));
        }
        if (matchingWordList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(matchingWordList);
    }
}
