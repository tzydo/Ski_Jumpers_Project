package com.pl.skijumping.batch.datareaderjob.reader;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DataReaderBatch implements ItemReader<String> {
    private final String filePath;
    private final DiagnosticMonitor diagnosticMonitor;

    public DataReaderBatch(String filePath, DiagnosticMonitor diagnosticMonitor) {
        this.filePath = filePath;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Override
    public String read() {
        diagnosticMonitor.logInfo(String.format("Start reading from file %s", filePath));
        List<String> fileLines;
        try {
            fileLines = Files.readAllLines(Paths.get(filePath));
            diagnosticMonitor.logInfo(String.format("Found %d lines to convert", fileLines.size()));
        } catch (IOException e) {
            diagnosticMonitor.logError(String.format("Cannot read content from file %s", filePath), getClass());
            return null;
        }

        if (fileLines == null || fileLines.isEmpty()) {
            diagnosticMonitor.logInfo("File content is null or empty");
            return null;
        }

        String fileContent = String.join("", fileLines);

        diagnosticMonitor.logInfo(String.format("Finish reading file from: %s", filePath));
        return fileContent.replace("  ", "");
    }
}
