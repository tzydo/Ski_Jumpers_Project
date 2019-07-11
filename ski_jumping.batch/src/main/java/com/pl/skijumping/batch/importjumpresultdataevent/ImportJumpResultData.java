package com.pl.skijumping.batch.importjumpresultdataevent;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.batch.reader.DataReader;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.service.JumpResultService;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
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

        diagnosticMonitor.logInfo("Start parsing data from file: " + filePath);
        return matchingWords.getJumpResultTemplate(readSource).stream()
                .filter(Objects::nonNull)
                .map(jumpResultParser::parseData)
                .collect(Collectors.toSet())
                .stream()
                    .filter(Objects::nonNull)
                    .map(jumpResult -> {
                        Optional<JumpResultDTO> foundJumpResult = jumpResultService.findByJumpResult(jumpResult);
                        if(foundJumpResult.isPresent()) {
                            return jumpResult.id(foundJumpResult.get().getId());
                        }
                        return jumpResult.id(jumpResultService.save(jumpResult).getId());
                    })
                    .collect(Collectors.toSet());
    }
}
