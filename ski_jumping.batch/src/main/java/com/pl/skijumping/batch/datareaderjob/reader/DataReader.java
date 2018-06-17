package com.pl.skijumping.batch.datareaderjob.reader;

import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

public class DataReader {
    private final String filePath;
    private final DiagnosticMonitor diagnosticMonitor;

    public DataReader(String filePath, DiagnosticMonitor diagnosticMonitor) {
        this.filePath = filePath;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    public String read() {
        diagnosticMonitor.logInfo(String.format("Start reading from file %s", filePath));
        List<String> fileLines;
        try {
            Optional<File> file = FileUtil.getFile(filePath);
            if (!file.isPresent()) {
                String errorMessage = "Cannot read from not existing file";
                diagnosticMonitor.logError(errorMessage, getClass());
                return null;
            }

            fileLines = Files.readAllLines(file.get().toPath());
            diagnosticMonitor.logInfo(String.format("Found %d lines to convert", fileLines.size()));
        } catch (IOException e) {
            diagnosticMonitor.logError(String.format("Cannot read content from file %s", filePath), getClass());
            return null;
        }

        if (fileLines.isEmpty()) {
            diagnosticMonitor.logInfo("File content is null or empty");
            return null;
        }

        String fileContent = String.join("", fileLines);

        diagnosticMonitor.logInfo(String.format("Finish reading file from: %s", filePath));
        return fileContent.replaceAll("   ", "");
    }
}
