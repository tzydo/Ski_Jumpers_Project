package com.pl.skijumping.batch.reader;

import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DataReader {
    private final DiagnosticMonitor diagnosticMonitor;

    public DataReader(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    public String read(String filePath) {
        diagnosticMonitor.logInfo(String.format("Start reading from file %s", filePath));
        List<String> fileLines;
        try {
            Path pathToFile = FileUtil.getPath(filePath);
            if (!pathToFile.toFile().exists()) {
                String errorMessage = "Cannot read from not existing file";
                diagnosticMonitor.logError(errorMessage, getClass());
                return null;
            }

            fileLines = Files.readAllLines(pathToFile);
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
        fileContent = fileContent.replaceAll("  ", "");
        return fileContent.replaceAll("   ", "");
    }
}
