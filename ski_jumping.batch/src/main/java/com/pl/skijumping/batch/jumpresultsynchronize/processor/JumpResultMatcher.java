package com.pl.skijumping.batch.jumpresultsynchronize.processor;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.dto.SkiJumperDTO;
import com.pl.skijumping.service.SkiJumperService;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("squid:S3516")
public class JumpResultMatcher {
    private final DiagnosticMonitor diagnosticMonitor;
    private final SkiJumperService skiJumperService;
    private final MatchingWords matchingWords;

    public JumpResultMatcher(DiagnosticMonitor diagnosticMonitor, SkiJumperService skiJumperService) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.skiJumperService = skiJumperService;
        matchingWords = new MatchingWords(this.diagnosticMonitor);
    }

    public JumpResultDTO matchJumperData(String words, Long raceDataId) {
        Optional<List<String>> jumpResultDataSecondStep = matchingWords.getJumpResultDataSecondStep(words);
        if (!jumpResultDataSecondStep.isPresent()) {
            return null;
        }

        NameConverter skiJumperNameConverter = new NameConverter(diagnosticMonitor);
        String skiJumperName = skiJumperNameConverter.convert(matchingWords.getSkiJumperName(words));
        if (skiJumperName == null || skiJumperName.isEmpty()) {
            diagnosticMonitor.logError("Skijumper name cannot be null", this.getClass());
            return null;
        }

        SkiJumperDTO skiJumper = skiJumperService.findOneByName(skiJumperName).orElse(null);
        if (skiJumper == null) {
            skiJumper = createSkiJumper(jumpResultDataSecondStep.get(), skiJumperName);
        }

        return createJumpResult(jumpResultDataSecondStep.get(), skiJumper.getId(), raceDataId);
    }

    private JumpResultDTO createJumpResult(List<String> resultDataSecondStepWords, Long skiJumperId, Long raceDataId) {
        JumpResultDTO jumpResultDTO = new JumpResultDTO();
        jumpResultDTO.jumperId(skiJumperId);
        jumpResultDTO.dataRaceId(raceDataId);
        jumpResultDTO.setRank(getValue(resultDataSecondStepWords, 0));
        jumpResultDTO.setFirstJump(getDoubleValue(resultDataSecondStepWords, 5));
        jumpResultDTO.setPointsForFirstJump(getDoubleValue(resultDataSecondStepWords, 6));
        jumpResultDTO.setSecondJump(getDoubleValue(resultDataSecondStepWords, 7));
        jumpResultDTO.setPointsForSecondJump(getDoubleValue(resultDataSecondStepWords, 8));
        jumpResultDTO.setTotalPoints(getDoubleValue(resultDataSecondStepWords, 9));

        return jumpResultDTO;
    }

    private SkiJumperDTO createSkiJumper(List<String> words, String name) {
        SkiJumperDTO skiJumperDTO = new SkiJumperDTO();
        skiJumperDTO.setBib(getValue(words, 1));
        skiJumperDTO.setFisCode(getValue(words, 2));
        skiJumperDTO.setName(name);

        return skiJumperService.save(skiJumperDTO);
    }

    private Integer getValue(List<String> resultDataSecondStep, int index) {
        if (resultDataSecondStep.isEmpty() || resultDataSecondStep.size() < index) {
            diagnosticMonitor.logWarn("Cannot get value from list where size is less than the index");
            return null;
        }
        return Integer.parseInt(resultDataSecondStep.get(index));
    }

    private Double getDoubleValue(List<String> resultDataSecondStep, int index) {
        if (resultDataSecondStep.isEmpty() || resultDataSecondStep.size() < index) {
            diagnosticMonitor.logWarn("Cannot get value from list where size is less than the index");
            return null;
        }
        return Double.valueOf(resultDataSecondStep.get(index));
    }
}