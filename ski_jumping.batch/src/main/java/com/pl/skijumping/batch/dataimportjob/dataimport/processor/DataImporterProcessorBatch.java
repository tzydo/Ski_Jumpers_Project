package com.pl.skijumping.batch.dataimportjob.dataimport.processor;

import com.pl.skijumping.client.HtmlDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class DataImporterProcessorBatch implements ItemProcessor<Boolean, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataImporterProcessorBatch.class);
    private final String host;
    private final String fileName;

    public DataImporterProcessorBatch(String host, String fileName) {
        this.host = host;
        this.fileName = fileName;
    }

    @Override
    public String process(Boolean readResult) throws Exception {
        HtmlDownloader fileDownloader = new HtmlDownloader(this.fileName, this.host);
        LOGGER.info("Start downloading html source");
        return fileDownloader.downloadSource();
    }
}