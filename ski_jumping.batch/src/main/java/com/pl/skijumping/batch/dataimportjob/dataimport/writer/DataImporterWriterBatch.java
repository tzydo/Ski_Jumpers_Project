package com.pl.skijumping.batch.dataimportjob.dataimport.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class DataImporterWriterBatch implements ItemWriter<String>{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataImporterWriterBatch.class);

    @Override
    public void write(List<? extends String> list) throws Exception {
        if(list == null || list.isEmpty()) {
            LOGGER.error("Job data import FAILED");
        }

        LOGGER.info("Job data import successfully finished");
    }
}
