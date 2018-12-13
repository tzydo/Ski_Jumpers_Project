package com.pl.skijumping.batch.matchingword;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;

import java.util.HashSet;
import java.util.Set;

public class MatchingWords {
    private final DiagnosticMonitor diagnosticMonitor;

    public MatchingWords(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    public Set<String> getEventIds(String words) {
        if (isEmpty(words)) return new HashSet<>();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.EVENT_ID, false, 1);
    }

    public Set<String> getRaceDataFirstStep(String words) {
        if (isEmpty(words)) return new HashSet<>();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIRST_STEP_DATA_RACE, false);
    }

    public Set<String> getRaceDataSecondStep(String words) {
        if (isEmpty(words)) return new HashSet<>();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.SECOND_STEP_DATA_RACE, false);
    }

    public Set<String> getRaceDataThirdStep(String words) {
        if (isEmpty(words)) return new HashSet<>();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.THIRD_STEP_DATA_RACE, true);
    }

    public Set<String> getRaceDataFourthStep(String words) {
        if (isEmpty(words)) return new HashSet<>();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FOURTH_STEP_DATA_RACE, true);
    }

    public Set<String> getRaceDate(String words) {
        if (isEmpty(words)) return new HashSet<>();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.SEASON_DATE, true);
    }

    public Set<String> getJumpResultDataFilter(String words) {
        if (isEmpty(words)) return new HashSet<>();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.RESULT_SYNCHRONIZE_FILTER, false, 1);
    }

    public Set<String> getJumpResultDataFirstStep(String words) {
        if (isEmpty(words)) return new HashSet<>();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.RESULT_SYNCHRONIZE_FIRST_FILTER, false, 1);
    }

    public Set<String> getJumpResultDataSecondStep(String words) {
        if (isEmpty(words)) return new HashSet<>();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.RESULT_SYNCHRONIZE_SECOND_FILTER, false, 1);
    }

    public String getSkiJumperName(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        Set<String> skiJumperName = findMatchingWords.getMatchingWords(words, RegexpPattern.RESULT_SYNCHRONIZE_SKIJUMPER_NAME, false, 1);

        return skiJumperName.stream().findFirst().orElse(null);
    }

    private boolean isEmpty(String words) {
        if (words == null || words.isEmpty()) {
            diagnosticMonitor.logError("Cannot find matching words from null", getClass());
            return true;
        }
        return false;
    }
}
