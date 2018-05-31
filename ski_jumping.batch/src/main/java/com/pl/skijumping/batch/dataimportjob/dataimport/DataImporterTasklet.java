package com.pl.skijumping.batch.dataimportjob.dataimport;

import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
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
    private final DiagnosticMonitor diagnosticMonitor;

    public DataImporterTasklet(String host, String directory, String fileName, DiagnosticMonitor diagnosticMonitor) {
        this.host = host;
        this.directory = directory;
        this.fileName = fileName;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        FilePreparation filePreparation = new FilePreparation(directory, fileName);
        if (!filePreparation.prepare()) {
            stepContribution.setExitStatus(ExitStatus.FAILED);
            return RepeatStatus.FINISHED;
        }

        HtmlDownloader fileDownloader = new HtmlDownloader(this.fileName, this.host);
        diagnosticMonitor.logInfo("Start downloading html source");
        String filePath = fileDownloader.downloadSource();

        if (filePath == null) {
            diagnosticMonitor.logError("Job data import FAILED", getClass());
            stepContribution.setExitStatus(ExitStatus.FAILED);
            return RepeatStatus.FINISHED;
        }

        diagnosticMonitor.logInfo("Job data import successfully finished");
        stepContribution.setExitStatus(ExitStatus.COMPLETED);
        return RepeatStatus.FINISHED;
    }
}
