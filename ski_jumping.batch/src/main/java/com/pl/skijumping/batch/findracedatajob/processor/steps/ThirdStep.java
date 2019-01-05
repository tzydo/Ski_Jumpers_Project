//package com.pl.skijumping.batch.findracedatajob.processor.steps;
//
//import com.pl.skijumping.batch.matchingword.MatchingWords;
//import com.pl.skijumping.common.exception.InternalServiceException;
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//import com.pl.skijumping.dto.DataRaceDTO;
//
//import java.util.List;
//import java.util.Optional;
//
//public class ThirdStep {
//    private final String words;
//    private final DiagnosticMonitor diagnosticMonitor;
//
//
//    public ThirdStep(String words, DiagnosticMonitor diagnosticMonitor) {
//        this.words = words;
//        this.diagnosticMonitor = diagnosticMonitor;
//    }
//
//    public DataRaceDTO setValues(DataRaceDTO dataRaceDTO) throws InternalServiceException {
//        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
//        Optional<List<String>> dataList = matchingWords.getRaceDataThirdStep(words);
//        if (!dataList.isPresent()) {
//            diagnosticMonitor.logError("Cannot find any matching words in three step", getClass());
//            return null;
//        }
//
//        dataRaceDTO.setRaceId(getRaceId(dataList.get()));
//        dataRaceDTO.setShortCountryName(getValue(dataList.get(), 1));
//        dataRaceDTO.setCity(getValue(dataList.get(), 2));
//
//        return dataRaceDTO;
//    }
//
//    private String getValue(List<String> words, int index) {
//        if (words == null || words.isEmpty() || words.size() < index) {
//            return null;
//        }
//
//        return words.get(index);
//    }
//
//    private Long getRaceId(List<String> dataList) throws InternalServiceException {
//        Long raceId;
//        try {
//            raceId = Long.parseLong(dataList.get(0));
//        } catch (NumberFormatException e) {
//            String errorMessage = String.format("Cannot parse word: %s to race id",
//                    dataList.get(0));
//            diagnosticMonitor.logError(errorMessage, getClass());
//            throw new InternalServiceException(errorMessage);
//        }
//        return raceId;
//    }
//}
