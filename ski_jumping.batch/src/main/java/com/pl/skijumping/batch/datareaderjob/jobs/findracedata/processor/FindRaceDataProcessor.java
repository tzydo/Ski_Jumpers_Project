package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor;

import com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor.steps.FourthStep;
import com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor.steps.ThirdStep;
import com.pl.skijumping.batch.datareaderjob.reader.matchingword.MatchingWords;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.domain.dto.DataRaceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class FindRaceDataProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindRaceDataProcessor.class);
    private final String readWords;

    public FindRaceDataProcessor(String readWords) {
        this.readWords = readWords;
    }

    public List<DataRaceDTO> findData() throws InternalServiceException {

        MatchingWords matchingWords = new MatchingWords();
        Optional<List<String>> dateList = matchingWords.getRaceDate(readWords);
        if (!dateList.isPresent()) {
            LOGGER.error("Cannot find matching dates in find race data processor");
        }

        LocalDateSetter localDateSetter = new LocalDateSetter(dateList.get());
        LocalDate raceDate = localDateSetter.setDate();
        if (raceDate == null) {
            return new ArrayList<>();
        }

        Optional<List<String>> dataList = matchingWords.getRaceDataSecondStep(readWords);
        if (!dataList.isPresent()) {
            LOGGER.error("Not found any matching words in find race data processor");
            return new ArrayList<>();
        }

        List<DataRaceDTO> dataRaceDTOList = new ArrayList<>();

        for (String foundMatchingWords : dataList.get()) {
            DataRaceDTO dataRaceDTO = new DataRaceDTO();
            dataRaceDTO.setDate(raceDate);

            ThirdStep thirdStep = new ThirdStep(foundMatchingWords);
            dataRaceDTO = thirdStep.setValues(dataRaceDTO);

            FourthStep fourthStep = new FourthStep(foundMatchingWords);
            fourthStep.setValue(dataRaceDTO);
            dataRaceDTOList.add(dataRaceDTO);
        }

        return dataRaceDTOList;
    }
}
