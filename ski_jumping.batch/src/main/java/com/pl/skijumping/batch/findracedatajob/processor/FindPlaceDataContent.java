package com.pl.skijumping.batch.findracedatajob.processor;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;

import java.nio.file.Path;

public class FindPlaceDataContent {

    private final DiagnosticMonitor diagnosticMonitor;

    public FindPlaceDataContent(DiagnosticMonitor diagnosticMonitor, Path filePath) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    public String getPlaceData(Path filePath) {
        if(filePath == null || !filePath.toFile().isFile()) {
            diagnosticMonitor.logWarn("Cannot read data from not existing file: " + filePath.toFile().getPath());
            return null;
        }
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);

    }
}
