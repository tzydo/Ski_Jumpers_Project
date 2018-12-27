//package com.pl.skijumping.batch.datasynchronize.reader;
//
//import com.pl.skijumping.batch.filereader.reader.DataReader;
//import com.pl.skijumping.batch.matchingword.MatchingWords;
//import com.pl.skijumping.common.util.FileUtil;
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@SuppressWarnings("squid:S135")
//public class BasicDataSynchronizeReader {
//    private final String directoryPath;
//    private final DiagnosticMonitor diagnosticMonitor;
//
//    public BasicDataSynchronizeReader(String directoryPath, DiagnosticMonitor diagnosticMonitor) {
//        this.directoryPath = directoryPath;
//        this.diagnosticMonitor = diagnosticMonitor;
//    }
//
//    public List<String> synchronize() {
//        List<File> filesFromPath = FileUtil.getFiles(directoryPath);
//        if (filesFromPath == null || filesFromPath.isEmpty()) {
//            return new ArrayList<>();
//        }
//
//        List<String> wordsList = new ArrayList<>();
//        for (File file : filesFromPath) {
//            String filePath = directoryPath + File.separator + file.getName();
//            DataReader dataReader = new DataReader(diagnosticMonitor);
//            String fileContent = dataReader.read(filePath);
//            if (fileContent == null || fileContent.isEmpty()) {
//                diagnosticMonitor.logError(String.format("Cannot read basic data from file: %s", file.getPath()), getClass());
//                continue;
//            }
//
//            MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
//            Optional<List<String>> raceDataFirstStep = matchingWords.getRaceDataFirstStep(fileContent);
//            if (!raceDataFirstStep.isPresent()) {
//                diagnosticMonitor.logError(String.format("Not found matching words for first step basic data synchronize job in file: %s", file.getPath()), getClass());
//                continue;
//            }
//            wordsList.addAll(raceDataFirstStep.get());
//        }
//
//        return wordsList;
//    }
//}
