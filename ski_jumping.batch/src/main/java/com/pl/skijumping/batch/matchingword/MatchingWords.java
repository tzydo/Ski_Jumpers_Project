package com.pl.skijumping.batch.matchingword;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;

import java.util.List;
import java.util.Optional;

public class MatchingWords {
    private final DiagnosticMonitor diagnosticMonitor;

    public MatchingWords(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    public Optional<List<String>> getTournamentYears(String words) {
        if (isEmpty(words)) return Optional.empty();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getSeasonData(words, RegexpPattern.TOURNAMENT_YEAR, true);
    }

    public Optional<List<String>> getRaceDataFirstStep(String words) {
        if (isEmpty(words)) return Optional.empty();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getSeasonData(words, RegexpPattern.FIRST_STEP_DATA_RACE, false);
    }

    public Optional<List<String>> getRaceDataSecondStep(String words) {
        if (isEmpty(words)) return Optional.empty();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getSeasonData(words, RegexpPattern.SECOND_STEP_DATA_RACE, false);
    }

    public Optional<List<String>> getRaceDataThirdStep(String words) {
        if (isEmpty(words)) return Optional.empty();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getSeasonData(words, RegexpPattern.THIRD_STEP_DATA_RACE, true);
    }

    public Optional<List<String>> getRaceDataFourthStep(String words) {
        if (isEmpty(words)) return Optional.empty();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getSeasonData(words, RegexpPattern.FOURTH_STEP_DATA_RACE, true);
    }

    public Optional<List<String>> getRaceDate(String words) {
        if (isEmpty(words)) return Optional.empty();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getSeasonData(words, RegexpPattern.SEASON_DATE, true);
    }

    public Optional<List<String>> getJumpResultDataFilter(String words) {
        if (isEmpty(words)) return Optional.empty();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getSeasonData(words, RegexpPattern.RESULT_SYNCHRONIZE_FILTER, false,1);
    }

    public Optional<List<String>> getJumpResultDataFirstStep(String words) {
        if (isEmpty(words)) return Optional.empty();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getSeasonData(words, RegexpPattern.RESULT_SYNCHRONIZE_FIRST_FILTER, false, 1);
    }

    public Optional<List<String>> getJumpResultDataSecondStep(String words) {
        if (isEmpty(words)) return Optional.empty();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getSeasonData(words, RegexpPattern.RESULT_SYNCHRONIZE_SECOND_FILTER, false, 1);
    }

    public String getSkiJumperName(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        Optional<List<String>> skiJumperName = findMatchingWords.getSeasonData(words, RegexpPattern.RESULT_SYNCHRONIZE_SKIJUMPER_NAME, false, 1);

        return skiJumperName.map(strings -> strings.get(0)).orElse(null);
    }

    private boolean isEmpty(String words) {
        if (words == null || words.isEmpty()) {
            diagnosticMonitor.logError("Cannot find matching words from null", getClass());
            return true;
        }
        return false;
    }
}
