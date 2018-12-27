package com.pl.skijumping.batch.eventimporterjob.tasklet;

import com.pl.skijumping.batch.reader.DataReader;
import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.common.util.Pair;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class EventImporter {

    private final DiagnosticMonitor diagnosticMonitor;
    private MatchingWords matchingWords;

    EventImporter(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.matchingWords = new MatchingWords(diagnosticMonitor);
    }

    List<Pair<String, String>> importEvents(Path filePath) {
        if (filePath == null || !filePath.toFile().exists()) {
            diagnosticMonitor.logWarn("Cannot import events from null");
            return new ArrayList<>();
        }

        DataReader dataReader = new DataReader(diagnosticMonitor);
        String readLines = dataReader.read(filePath.toString());
        if (readLines == null) {
            diagnosticMonitor.logWarn("Not found any events id in file: " + filePath.toFile().getName());
            return new ArrayList<>();
        }

        return matchingWords.getEventIds(readLines).stream()
                .map(eventId -> new Pair<>(FileUtil.getFileNameWithoutExtensions(filePath), eventId))
                .collect(Collectors.toList());
    }
}
