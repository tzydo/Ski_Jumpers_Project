package com.pl.skijumping.batch.findracedatajob.processor;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.batch.reader.DataReader;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

class FindRaceDataContent {

    private final DiagnosticMonitor diagnosticMonitor;

    FindRaceDataContent(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    Set<String> findTemplateData(Path filePath) {
        if(filePath == null) {
            diagnosticMonitor.logWarn("Cannot read from null path!");
            return new HashSet<>();
        }
        String fileContent = getFileContent(filePath);
        if (fileContent == null || fileContent.isEmpty()) {
            diagnosticMonitor.logError("Cannot read words from file: " + filePath, getClass());
            return new HashSet<>();
        }

        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Set<String> raceDataTemplates = matchingWords.getRaceDataTemplate(fileContent);
        if (raceDataTemplates == null) {
            diagnosticMonitor.logError("Not found matching words for templates in data race job", getClass());
            return new HashSet<>();
        }

        return raceDataTemplates;
    }

    private String getFileContent(Path filePath) {
        DataReader dataReader = new DataReader(diagnosticMonitor);
        return dataReader.read(filePath.toString());
    }
}
