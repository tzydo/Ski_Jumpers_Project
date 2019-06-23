package com.pl.skijumping.batch.importjumpresultdataevent;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.batch.reader.DataReader;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.service.JumpResultService;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

class ImportJumpResultData {
    private final DiagnosticMonitor diagnosticMonitor;
    private final JumpResultService jumpResultService;

    ImportJumpResultData(DiagnosticMonitor diagnosticMonitor, JumpResultService jumpResultService) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.jumpResultService = jumpResultService;
    }

    Set<JumpResultDTO> importData(String filePath) {
        if (filePath == null || filePath.equals(" ") || filePath.isEmpty()) {
            return new HashSet<>();
        }

        DataReader dataReader = new DataReader(diagnosticMonitor);
        String readSource = dataReader.read(filePath);
        if (readSource == null || readSource.isEmpty()) {
            return new HashSet<>();
        }

        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        JumpResultParser jumpResultParser = new JumpResultParser(diagnosticMonitor);

        return matchingWords.getJumpResultTemplate(readSource).stream()
                .filter(Objects::nonNull)
                .map(jumpResultParser::parseData)
                .collect(Collectors.toSet())
                .stream()
                    .filter(Objects::nonNull)
                    .map(jumpResultService::save)
                    .collect(Collectors.toSet());
    }
}
