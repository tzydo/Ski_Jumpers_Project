package com.pl.skijumping.batch.datasynchronize.reader;

import com.pl.skijumping.batch.datareaderjob.reader.DataReader;
import com.pl.skijumping.batch.datareaderjob.reader.matchingword.MatchingWords;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BasicDataSynchronizeReader {
    private final String directoryPath;
    private final DiagnosticMonitor diagnosticMonitor;

    public BasicDataSynchronizeReader(String directoryPath, DiagnosticMonitor diagnosticMonitor) {
        this.directoryPath = directoryPath;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    public List<String> synchronize() {
        Optional<List<File>> filesFromPath = FileUtil.getFilesFromPath(directoryPath);
        if (!filesFromPath.isPresent()) {
            return new ArrayList<>();
        }

        List<String> wordsList = new ArrayList<>();
        for (File file : filesFromPath.get()) {
            String filePath = directoryPath + File.separator + file.getName();
            DataReader dataReader = new DataReader(filePath, diagnosticMonitor);
            String fileContent = dataReader.read();
            if (fileContent == null || fileContent.isEmpty()) {
                diagnosticMonitor.logError(String.format("Cannot read basic data from file: %s", file.getPath()), getClass());
                continue;
            }

            MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
            Optional<List<String>> raceDataFirstStep = matchingWords.getRaceDataFirstStep(fileContent);
            if (!raceDataFirstStep.isPresent()) {
                diagnosticMonitor.logError(String.format("Not found matching words for first step basic data synchronize job in file: %s", file.getPath()), getClass());
                continue;
            }
            wordsList.addAll(raceDataFirstStep.get());
        }

        return wordsList;
    }
}
