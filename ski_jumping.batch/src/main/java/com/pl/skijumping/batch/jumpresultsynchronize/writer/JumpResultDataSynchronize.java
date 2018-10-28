package com.pl.skijumping.batch.jumpresultsynchronize.writer;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.service.JumpResultService;
import com.pl.skijumping.service.JumpResultToDataRaceService;
import com.pl.skijumping.service.JumpResultToSkiJumperService;
import com.pl.skijumping.service.SkiJumperService;

import java.util.List;
import java.util.Optional;

class JumpResultDataSynchronize {
    private static final String UNNECESSARY_WORD = "&nbsp;";

    private final DiagnosticMonitor diagnosticMonitor;
    private final Long raceDataId;
    private JumpResultMatcher jumpResultMatcher;


    JumpResultDataSynchronize(DiagnosticMonitor diagnosticMonitor,
                                     SkiJumperService skiJumperService,
                                     Long raceDataId,
                                     JumpResultToDataRaceService jumpResultToDataRaceService,
                                     JumpResultToSkiJumperService jumpResultToSkiJumperService,
                                     JumpResultService jumpResultService) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.raceDataId = raceDataId;
        this.jumpResultMatcher = new JumpResultMatcher(diagnosticMonitor, skiJumperService,
                jumpResultToDataRaceService, jumpResultToSkiJumperService, jumpResultService);
    }

    void transformData(String data) {
        if (data == null || data.isEmpty()) {
            diagnosticMonitor.logWarn("Cannot match words from null");
            return;
        }

        String words = removeUnnecessaryWords(data);
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Optional<List<String>> resultDataFirstFilter = matchingWords.getJumpResultDataFilter(words);

        if (!resultDataFirstFilter.isPresent()) {
            return;
        }

        Optional<List<String>> resultDataBodyList = matchingWords.
                getJumpResultDataFirstStep(resultDataFirstFilter.get().get(0));

        if (!resultDataBodyList.isPresent()) {
            return;
        }

        resultDataBodyList.get().forEach(this::createJumpResultFromString);
    }

    private void createJumpResultFromString(String words) {
        jumpResultMatcher.matchJumperData(words, this.raceDataId);
    }

    private String removeUnnecessaryWords(String words) {
        return words.replaceAll(UNNECESSARY_WORD, "");
    }
}