package com.pl.skijumping.batch.jumpresultsynchronize.writer;

import com.pl.skijumping.batch.jumpresultsynchronize.writer.creator.JumpResultCreator;
import com.pl.skijumping.batch.jumpresultsynchronize.writer.creator.SkiJumperCreator;
import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.dto.JumpResultToDataRaceDTO;
import com.pl.skijumping.dto.JumpResultToSkiJumperDTO;
import com.pl.skijumping.dto.SkiJumperDTO;
import com.pl.skijumping.service.JumpResultService;
import com.pl.skijumping.service.JumpResultToDataRaceService;
import com.pl.skijumping.service.JumpResultToSkiJumperService;
import com.pl.skijumping.service.SkiJumperService;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("squid:S3516")
class JumpResultMatcher {
    private final DiagnosticMonitor diagnosticMonitor;
    private final SkiJumperService skiJumperService;
    private final MatchingWords matchingWords;
    private final JumpResultToDataRaceService jumpResultToDataRaceService;
    private final JumpResultToSkiJumperService jumpResultToSkiJumperService;
    private final JumpResultService jumpResultService;

    JumpResultMatcher(DiagnosticMonitor diagnosticMonitor,
                      SkiJumperService skiJumperService,
                      JumpResultToDataRaceService jumpResultToDataRaceService,
                      JumpResultToSkiJumperService jumpResultToSkiJumperService,
                      JumpResultService jumpResultService) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.skiJumperService = skiJumperService;
        this.jumpResultToDataRaceService = jumpResultToDataRaceService;
        this.jumpResultToSkiJumperService = jumpResultToSkiJumperService;
        this.jumpResultService = jumpResultService;
        matchingWords = new MatchingWords(diagnosticMonitor);
    }

    void matchJumperData(String words, Long raceDataId) {
        Optional<List<String>> jumpResultDataSecondStep = matchingWords.getJumpResultDataSecondStep(words);
        if (!jumpResultDataSecondStep.isPresent()) {
            return;
        }

        String skiJumperName = NameConverter.convert(matchingWords.getSkiJumperName(words));
        if (skiJumperName == null || skiJumperName.isEmpty()) {
            diagnosticMonitor.logWarn("Skijumper name cannot be null");
            return;
        }

        SkiJumperCreator skiJumperCreator = new SkiJumperCreator(skiJumperService);
        SkiJumperDTO skiJumperDTO = skiJumperCreator.createSkiJumper(jumpResultDataSecondStep.get(), skiJumperName);

        JumpResultDTO jumpResultDTO = JumpResultCreator.createJumpResult(jumpResultDataSecondStep.get());
        Optional<JumpResultDTO> foundJumpResultDTO = jumpResultService.findBy(jumpResultDTO);
        jumpResultDTO = foundJumpResultDTO.isPresent() ? foundJumpResultDTO.get() : jumpResultService.save(jumpResultDTO);

        createJumpResultToDataRace(raceDataId, jumpResultDTO);
        createJumpResultToSkiJumper(jumpResultDTO.getId(), skiJumperDTO.getId());
    }

    private void createJumpResultToDataRace(Long dataRaceId, JumpResultDTO jumpResult) {
        jumpResultToDataRaceService.save(
                new JumpResultToDataRaceDTO().dataRaceId(dataRaceId).jumpResultId(jumpResult.getId()));
    }

    private void createJumpResultToSkiJumper(Long jumpResultId, Long skiJumperId) {
        jumpResultToSkiJumperService.save(new JumpResultToSkiJumperDTO().jumpResultId(jumpResultId).skiJumperId(skiJumperId));
    }
}