//package com.pl.skijumping.batch.findracedatajob.processor.steps;
//
//import com.pl.skijumping.batch.matchingword.MatchingWords;
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//import com.pl.skijumping.dto.DataRaceDTO;
//
//import java.util.List;
//import java.util.Optional;
//
//public class FindCompetitionsData {
//    private final String words;
//    private final DiagnosticMonitor diagnosticMonitor;
//
//    public FindCompetitionsData(String readWords, DiagnosticMonitor diagnosticMonitor) {
//        this.words = readWords;
//        this.diagnosticMonitor = diagnosticMonitor;
//    }
//
//    public void setValue(DataRaceDTO dataRaceDTO) {
//        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
//        Optional<List<String>> matchingWordsList = matchingWords.getRaceDataFourthStep(words);
//        if (!matchingWordsList.isPresent()) {
//            diagnosticMonitor.logError(
//                    String.format("Not found any matching words for step four, in words: %s", words), getClass());
//            return;
//        }
//
//        dataRaceDTO.setCompetitionType(getValue(matchingWordsList.get(), 0));
//        dataRaceDTO.setCompetitionName(getValue(matchingWordsList.get(), 2));
//    }
//
//    private String getValue(List<String> words, int index) {
//        if (words == null || words.isEmpty() || words.size() < index) {
//            return null;
//        }
//
//        return words.get(index);
//    }
//}
