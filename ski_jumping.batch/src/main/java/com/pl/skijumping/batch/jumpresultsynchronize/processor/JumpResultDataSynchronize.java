package com.pl.skijumping.batch.jumpresultsynchronize.processor;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.service.SkiJumperService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

class JumpResultDataSynchronize {
    private static final String UNNECESSARY_WORD = "&nbsp;";

    private final DiagnosticMonitor diagnosticMonitor;
    private final Long raceDataId;
    private JumpResultMatcher jumpResultMatcher;
    private SkiJumperService skiJumperService;


    public JumpResultDataSynchronize(DiagnosticMonitor diagnosticMonitor, SkiJumperService skiJumperService, Long raceDataId) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.skiJumperService = skiJumperService;
        this.raceDataId = raceDataId;
    }

    List<JumpResultDTO> transformData(String words) {
        if (words == null || words.isEmpty()) {
            diagnosticMonitor.logWarn("Cannot match words from null");
            return new ArrayList<>();
        }

        words = removeUnnecessaryWords(words);
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Optional<List<String>> resultDataFirstFilter = matchingWords.getJumpResultDataFilter(words);

        if (!resultDataFirstFilter.isPresent()) {
            return new ArrayList<>();
        }

        Optional<List<String>> resultDataBodyList = matchingWords.
                getJumpResultDataFirstStep(resultDataFirstFilter.get().get(0));

        if (!resultDataBodyList.isPresent()) {
            return new ArrayList<>();
        }

        return resultDataBodyList.get().stream()
                .map(this::getSkiJumperFromData)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private JumpResultDTO getSkiJumperFromData(String words) {
        jumpResultMatcher = new JumpResultMatcher(diagnosticMonitor, skiJumperService);
        return jumpResultMatcher.matchJumperData(words, this.raceDataId);
    }

    private String removeUnnecessaryWords(String words) {
        return words.replaceAll(UNNECESSARY_WORD, "");
    }
}