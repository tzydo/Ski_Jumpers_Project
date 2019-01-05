//package com.pl.skijumping.batch.findracedatajob.processor;
//
//import com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor.steps.FindCompetitionsData;
//import com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor.steps.ThirdStep;
//import com.pl.skijumping.batch.matchingword.MatchingWords;
//import com.pl.skijumping.common.exception.InternalServiceException;
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//import com.pl.skijumping.dto.DataRaceDTO;
//import org.springframework.batch.item.support.ListItemReader;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//class FindRaceDataProcessor {
//    private final String readWords;
//    private final DiagnosticMonitor diagnosticMonitor;
//
//    FindRaceDataProcessor(String readWords, DiagnosticMonitor diagnosticMonitor) {
//        this.readWords = readWords;
//        this.diagnosticMonitor = diagnosticMonitor;
//    }
//
//    List<DataRaceDTO> findData() throws InternalServiceException {
//
////        DataReader dataReader = new DataReader(diagnosticMonitor);
////        String fileContent = dataReader.read(filePath);
////        if (fileContent == null || fileContent.isEmpty()) {
////            diagnosticMonitor.logError("Cannot read race data from file", getClass());
////            return;
////        }
////        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
////        Optional<List<String>> raceDataFirstStep = matchingWords.getRaceDataFirstStep(fileContent);
////        if (!raceDataFirstStep.isPresent()) {
////            diagnosticMonitor.logError("Not found matching words for first step data race job", getClass());
////            return;
////        }
////        filePathsItemReader = new ListItemReader<>(raceDataFirstStep.get());
//
//
//
//        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
//        Optional<List<String>> dateList = matchingWords.getRaceDate(readWords);
//        if (!dateList.isPresent()) {
//            diagnosticMonitor.logError("Cannot find matching dates in find race data writer", getClass());
//            return new ArrayList<>();
//        }
//
//        com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor.LocalDateSetter localDateSetter = new LocalDateSetter(dateList.get(), diagnosticMonitor);
//        LocalDate raceDate = localDateSetter.setDate();
//        if (raceDate == null) {
//            return new ArrayList<>();
//        }
//
//        Optional<List<String>> dataList = matchingWords.getRaceDataSecondStep(readWords);
//        if (!dataList.isPresent()) {
//            diagnosticMonitor.logError("Not found any matching words in find race data writer", getClass());
//            return new ArrayList<>();
//        }
//
//        List<DataRaceDTO> dataRaceDTOList = new ArrayList<>();
//
//        for (String foundMatchingWords : dataList.get()) {
//            DataRaceDTO dataRaceDTO = new DataRaceDTO();
//            dataRaceDTO.setDate(raceDate);
//
//            ThirdStep thirdStep = new ThirdStep(foundMatchingWords, diagnosticMonitor);
//            dataRaceDTO = thirdStep.setValues(dataRaceDTO);
//
//            FindCompetitionsData findCompetitionsData = new FindCompetitionsData(foundMatchingWords, diagnosticMonitor);
//            findCompetitionsData.setValue(dataRaceDTO);
//            dataRaceDTOList.add(dataRaceDTO);
//        }
//
//        return dataRaceDTOList;
//    }
//}
