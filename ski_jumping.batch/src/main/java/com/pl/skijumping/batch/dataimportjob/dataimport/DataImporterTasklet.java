package com.pl.skijumping.batch.dataimportjob.dataimport;

import com.pl.skijumping.client.HtmlDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class DataImporterTasklet implements Tasklet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataImporterTasklet.class);
    private final String host;
    private final String directory;
    private final String fileName;

    public DataImporterTasklet(String host, String directory, String fileName) {
        this.host = host;
        this.directory = directory;
        this.fileName = fileName;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        FilePreparation filePreparation = new FilePreparation(directory, fileName);
        if (!filePreparation.prepare()) {
            stepContribution.setExitStatus(ExitStatus.FAILED);
            return RepeatStatus.FINISHED;
        }

        HtmlDownloader fileDownloader = new HtmlDownloader(this.fileName, this.host);
        LOGGER.info("Start downloading html source");
        String filePath = fileDownloader.downloadSource();

        if (filePath == null) {
            LOGGER.error("Job data import FAILED");
            stepContribution.setExitStatus(ExitStatus.FAILED);
            return RepeatStatus.FINISHED;
        }

        LOGGER.info("Job data import successfully finished");
        stepContribution.setExitStatus(ExitStatus.COMPLETED);
        return RepeatStatus.FINISHED;
    }
}
