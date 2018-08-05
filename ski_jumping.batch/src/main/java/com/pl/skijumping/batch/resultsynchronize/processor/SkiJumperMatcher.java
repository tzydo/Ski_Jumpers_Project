package com.pl.skijumping.batch.resultsynchronize.processor;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.dto.SkiJumperDTO;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("squid:S3516")
public class SkiJumperMatcher {
    private final DiagnosticMonitor diagnosticMonitor;

    public SkiJumperMatcher(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    public Optional<SkiJumperDTO> matchJumperData(String words) {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Optional<List<String>> resultDataSecondStep = matchingWords.getResultDataSecondStep(words);
        if(!resultDataSecondStep.isPresent()) {
            return Optional.empty();
        }
// todo przemyslec co i jak
//        SkiJumperDTO skiJumperDTO = new SkiJumperDTO();
//        skiJumperDTO.setJumpResult(new JumpResultDTO());
//        skiJumperDTO.getJumpResult().setRank(getValue(resultDataSecondStep, 0));
//        skiJumperDTO.setBib(getValue(resultDataSecondStep, 1));
//        skiJumperDTO.setFisCode(getValue(resultDataSecondStep, 2));
//        skiJumperDTO.setName(matchingWords.getSkiJumperName(words));
//        skiJumperDTO.setNationality(resultDataSecondStep.get().get(4));
//        skiJumperDTO.getJumpResult().setFirstJump(getDoubleValue(resultDataSecondStep, 5));
//        skiJumperDTO.getJumpResult().setPointsForFirstJump(getDoubleValue(resultDataSecondStep, 6));
//        skiJumperDTO.getJumpResult().setSecondJump(getDoubleValue(resultDataSecondStep, 7));
//        skiJumperDTO.getJumpResult().setPointsForSecondJump(getDoubleValue(resultDataSecondStep, 8));
//        skiJumperDTO.getJumpResult().setTotalPoints(getDoubleValue(resultDataSecondStep, 9));

//        return Optional.of(skiJumperDTO);
        return null;
    }

    private Integer getValue(Optional<List<String>> resultDataSecondStep, int index) {
        if(resultDataSecondStep.get().size()<index) {
            diagnosticMonitor.logWarn("Cannot get value from list where size is less than the index");
            return null;
        }
        return Integer.parseInt(resultDataSecondStep.get().get(index));
    }

    private Double getDoubleValue(Optional<List<String>> resultDataSecondStep, int index) {
        if(resultDataSecondStep.get().size()<index) {
            diagnosticMonitor.logWarn("Cannot get value from list where size is less than the index");
            return null;
        }
        return Double.valueOf(resultDataSecondStep.get().get(index));
    }
}