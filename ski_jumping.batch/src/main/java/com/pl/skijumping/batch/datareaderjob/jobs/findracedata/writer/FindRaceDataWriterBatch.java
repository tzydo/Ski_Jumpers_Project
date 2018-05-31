package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.writer;

import com.pl.skijumping.domain.dto.DataRaceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class FindRaceDataWriterBatch implements ItemWriter<DataRaceDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindRaceDataWriterBatch.class);
//    private final DataRaceService dataRaceService;


    @Override
    public void write(List<? extends DataRaceDTO> dataRaceDTOList) throws Exception {
        if(dataRaceDTOList == null || dataRaceDTOList.isEmpty()) {
            LOGGER.error("Cannot save null object in find race data writer");
            return;
        }
        return;
    }
}
