package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor.steps;

import com.pl.skijumping.batch.datareaderjob.reader.matchingword.MatchingWords;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.domain.dto.DataRaceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ThirdStep {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThirdStep.class);
    private final String words;

    public ThirdStep(String words) {
        this.words = words;
    }

    public DataRaceDTO setValues(DataRaceDTO dataRaceDTO) throws InternalServiceException {
        MatchingWords matchingWords = new MatchingWords();
        Optional<List<String>> dataList = matchingWords.getRaceDataThirdStep(words);
        if (!dataList.isPresent()) {
            LOGGER.error("Cannot find any matching words in three step");
            return null;
        }

        dataRaceDTO.setRaceId(getRaceId(dataList.get()));
        dataRaceDTO.setShortCountryName(getValue(dataList.get(), 1));
        dataRaceDTO.setCity(getValue(dataList.get(), 2));

        return dataRaceDTO;
    }

    private String getValue(List<String> words, int index) {
        if(words == null || words.isEmpty() || words.size() < index) {
            return null;
        }

        return words.get(index);
    }

    private Integer getRaceId(List<String> dataList) throws InternalServiceException {
        Integer raceId;
        try {
            raceId = Integer.parseInt(dataList.get(0));
        } catch (NumberFormatException e) {
            throw new InternalServiceException(
                    String.format("Cannot parse word: %s to race id", dataList.get(0)));
        }
        return raceId;
    }
}
