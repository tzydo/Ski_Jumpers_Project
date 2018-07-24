package com.pl.skijumping.batch.resultsynchronize.processor;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.SkiJumperDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ResultDataSynchronize {
    private static final String UNNECESSARY_WORD = "&nbsp;";
    private final DiagnosticMonitor diagnosticMonitor;
    private final SkiJumperMatcher skiJumperMatcher;

    public ResultDataSynchronize(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.skiJumperMatcher = new SkiJumperMatcher(diagnosticMonitor);
    }

    List<SkiJumperDTO> transformData(String words) {
        if(words == null || words.isEmpty()) {
            diagnosticMonitor.logWarn("Cannot match words from null");
            return new ArrayList<>();
        }

        words = remoteUnnecessaryWords(words);
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Optional<List<String>> resultDataFirstFilter = matchingWords.getResultDataFilter(words);

        if(!resultDataFirstFilter.isPresent()) {
            return new ArrayList<>();
        }

        Optional<List<String>> resultDataFirstStep = matchingWords.getResultDataFirstStep(resultDataFirstFilter.get().get(0));
        if(!resultDataFirstFilter.isPresent()) {
            return new ArrayList<>();
        }

        List<SkiJumperDTO> skiJumperDTOList = new ArrayList<>();
        for(String skiJumperMatchingWords : resultDataFirstStep.get()) {
            Optional<SkiJumperDTO> skiJumperDTO = getSkiJumperFromData(skiJumperMatchingWords);
            skiJumperDTO.ifPresent(skiJumperDTOList::add);
        }

        return skiJumperDTOList;
    }

    private Optional<SkiJumperDTO> getSkiJumperFromData(String words) {
        return this.skiJumperMatcher.matchJumperData(words);
    }

    private String remoteUnnecessaryWords(String words) {
        return words.replaceAll(UNNECESSARY_WORD, "");
    }
}