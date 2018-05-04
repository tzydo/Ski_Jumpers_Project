package com.pl.skijumping.batch.job.dataimport.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class DataImporterWriterBatch implements ItemWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataImporterWriterBatch.class);

    @Override
    public void write(List list) throws Exception {
        if(list == null || list.isEmpty()) {
            LOGGER.error("Job data import FAILED");
        }

        LOGGER.info("Job data import successfully finished");
    }
}
