package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor;

import com.pl.skijumping.domain.dto.DataRaceDTO;
import org.springframework.batch.item.ItemProcessor;

public class FindRaceDataProcessorBatch implements ItemProcessor<String, DataRaceDTO> {
    @Override
    public DataRaceDTO process(String readWords) throws Exception {
        if(readWords == null || readWords.isEmpty()) {
            return null;
        }

        FindRaceDataProcessor findRaceDataProcessor = new FindRaceDataProcessor(readWords);
        findRaceDataProcessor.findData();
        return null;
    }
}
