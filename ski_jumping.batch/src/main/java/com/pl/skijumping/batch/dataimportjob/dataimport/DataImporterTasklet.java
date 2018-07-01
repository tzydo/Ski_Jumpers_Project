package com.pl.skijumping.batch.dataimportjob.dataimport;

import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class DataImporterTasklet implements Tasklet {
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
        String errorMessage;
        FilePreparation filePreparation = new FilePreparation(directory, fileName);
        if (!filePreparation.prepare()) {
            ExitStatus e = new ExitStatus(ExitStatus.FAILED.getExitCode(), "cos");
            stepContribution.setExitStatus(e);
            errorMessage = String.format("Cannot create file %s in path: %s", fileName, directory);
            setExitStatus(stepContribution, errorMessage, ExitStatus.FAILED);
            return RepeatStatus.FINISHED;
        }

        HtmlDownloader fileDownloader = new HtmlDownloader();
        diagnosticMonitor.logInfo("Start downloading html source");
        String filePath = fileDownloader.downloadSource(this.fileName, this.host);

        if (filePath == null) {
            errorMessage = "Job data import FAILED";
            diagnosticMonitor.logError(errorMessage, getClass());
            setExitStatus(stepContribution, errorMessage, ExitStatus.FAILED);
            return RepeatStatus.FINISHED;
        }

        diagnosticMonitor.logInfo("Job data import successfully finished");
        stepContribution.setExitStatus(ExitStatus.COMPLETED);
        return RepeatStatus.FINISHED;
    }

    private void setExitStatus(StepContribution stepContribution, String errorMessage, ExitStatus status) {
        stepContribution.setExitStatus(new ExitStatus(status.getExitCode(), errorMessage));
    }
}
