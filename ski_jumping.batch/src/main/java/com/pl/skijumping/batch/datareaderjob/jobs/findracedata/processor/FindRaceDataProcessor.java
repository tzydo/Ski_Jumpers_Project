package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor;

import com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor.steps.FourthStep;
import com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor.steps.ThirdStep;
import com.pl.skijumping.batch.datareaderjob.reader.matchingword.MatchingWords;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class FindRaceDataProcessor {
    private final String readWords;
    private final DiagnosticMonitor diagnosticMonitor;

    public FindRaceDataProcessor(String readWords, DiagnosticMonitor diagnosticMonitor) {
        this.readWords = readWords;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    public List<DataRaceDTO> findData() throws InternalServiceException {

        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Optional<List<String>> dateList = matchingWords.getRaceDate(readWords);
        if (!dateList.isPresent()) {
            diagnosticMonitor.logError("Cannot find matching dates in find race data processor", getClass());
            return new ArrayList<>();
        }

        LocalDateSetter localDateSetter = new LocalDateSetter(dateList.get(), diagnosticMonitor);
        LocalDate raceDate = localDateSetter.setDate();
        if (raceDate == null) {
            return new ArrayList<>();
        }

        Optional<List<String>> dataList = matchingWords.getRaceDataSecondStep(readWords);
        if (!dataList.isPresent()) {
            diagnosticMonitor.logError("Not found any matching words in find race data processor", getClass());
            return new ArrayList<>();
        }

        List<DataRaceDTO> dataRaceDTOList = new ArrayList<>();

        for (String foundMatchingWords : dataList.get()) {
            DataRaceDTO dataRaceDTO = new DataRaceDTO();
            dataRaceDTO.setDate(raceDate);

            ThirdStep thirdStep = new ThirdStep(foundMatchingWords, diagnosticMonitor);
            dataRaceDTO = thirdStep.setValues(dataRaceDTO);

            FourthStep fourthStep = new FourthStep(foundMatchingWords, diagnosticMonitor);
            fourthStep.setValue(dataRaceDTO);
            dataRaceDTOList.add(dataRaceDTO);
        }

        return dataRaceDTOList;
    }
}
