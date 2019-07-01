package com.pl.skijumping.batch.importjumpresultteamdataevent;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.batch.reader.DataReader;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.service.JumpResultService;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ImportTeamJumpResult {
    private final DiagnosticMonitor diagnosticMonitor;
    private final JumpResultService jumpResultService;

    ImportTeamJumpResult(DiagnosticMonitor diagnosticMonitor, JumpResultService jumpResultService) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.jumpResultService = jumpResultService;
    }

    Set<JumpResultDTO> importTeamData(String filePath) {
        if (filePath == null || filePath.equals(" ") || filePath.isEmpty()) {
            return new HashSet<>();
        }

        DataReader dataReader = new DataReader(diagnosticMonitor);
        String readSource = dataReader.read(filePath);
        if (readSource == null || readSource.isEmpty()) {
            return new HashSet<>();
        }

        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        TeamJumpResultParser jumpResultParser = new TeamJumpResultParser(diagnosticMonitor);

        Set<JumpResultDTO> jumpResultDTOS = matchingWords.getTeamJumpResultGroups(readSource).stream()
                .filter(Objects::nonNull)
                .map(jumpResultParser::parseData)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        return jumpResultDTOS.stream()
                .map(jumpResultService::save)
                .collect(Collectors.toSet());
    }
}
