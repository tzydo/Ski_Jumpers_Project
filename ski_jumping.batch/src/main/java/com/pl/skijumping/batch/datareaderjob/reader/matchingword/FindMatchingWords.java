package com.pl.skijumping.batch.datareaderjob.reader.matchingword;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FindMatchingWords {
    private final DiagnosticMonitor diagnosticMonitor;

    public FindMatchingWords(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    public Optional<List<String>> getSeasonData(String words, String regexp, Boolean exactMatch) {
        if (words == null || words.isEmpty()) {
            diagnosticMonitor.logError("Cannot find matching words from null", getClass());
            return Optional.empty();
        }

        if (exactMatch) {
            return getExactMatchWordList(words, regexp);
        }
        return getMatchWordList(words, regexp);
    }

    private Optional<List<String>> getExactMatchWordList(String words, String regexp) {
        if (words == null || regexp == null) {
            diagnosticMonitor.logError("Wrong parameters in matching words method. Parameter cannot be null", getClass());
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
            diagnosticMonitor.logError("Wrong parameters in matching words method. Parameter cannot be null", getClass());
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
