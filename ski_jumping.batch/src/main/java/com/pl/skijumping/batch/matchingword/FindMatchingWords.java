package com.pl.skijumping.batch.matchingword;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

class FindMatchingWords {
    private final DiagnosticMonitor diagnosticMonitor;

    public FindMatchingWords(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    public Set<String> getMatchingWords(String words, String regexp, Boolean exactMatch, Integer additionalParam) {
        if (words == null || words.isEmpty()) {
            diagnosticMonitor.logError("Cannot find matching words from null", getClass());
            return new HashSet<>();
        }

        if (exactMatch) {
            return getExactMatchingWords(words, regexp);
        }
        return getMatchingWords(words, regexp, additionalParam);
    }

    private Set<String> getExactMatchingWords(String words, String regexp) {
        if (words == null || regexp == null) {
            diagnosticMonitor.logError("Incorrect parameters in matching words method. Parameter cannot be null", getClass());
            return new HashSet<>();
        }

        Pattern pattern = Pattern.compile(regexp);
        Set<String> matchingWordList = new HashSet<>();
        Matcher matcher = pattern.matcher(words);

        while (matcher.find()) {
            IntStream.range(0, matcher.groupCount()).forEach(increment -> {
                String group = matcher.group(increment);
                if (!group.contains("<") && !group.contains(">")) {
                    matchingWordList.add(group);
                }
            });
        }

        return matchingWordList;
    }

    private Set<String> getMatchingWords(String words, String regexp, Integer additionalOption) {
        if (words == null || regexp == null) {
            diagnosticMonitor.logError("Wrong parameters in matching words method. Parameter cannot be null", getClass());
            return new HashSet<>();
        }
        Pattern pattern = Pattern.compile(regexp);
        Set<String> matchingWords = new HashSet<>();
        Matcher matcher = pattern.matcher(words);
        int matchingStandard = getAdditionalOption(additionalOption);

        while (matcher.find()) {
            matchingWords.add(matcher.group(matchingStandard));
        }

        return matchingWords;
    }

    private int getAdditionalOption(Integer additionalValue) {
        if (additionalValue == null) {
            return 0;
        }

        return additionalValue;
    }
}
