package com.pl.skijumping.batch.eventimportjob.tasklet;

import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class EventIdImporterTaskletBatch implements Tasklet {
    private final HtmlDownloader htmlDownloader;
    private final String directory;
    private final DiagnosticMonitor diagnosticMonitor;
    private final String host;

    public EventIdImporterTaskletBatch(HtmlDownloader htmlDownloader,
                                       String directory,
                                       DiagnosticMonitor diagnosticMonitor,
                                       String host) {
        this.htmlDownloader = htmlDownloader;
        this.directory = directory;
        this.diagnosticMonitor = diagnosticMonitor;
        this.host = host;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        EventImporterTasklet eventImporterTasklet = new EventImporterTasklet(diagnosticMonitor, directory, htmlDownloader, host);
        ExitStatus exitStatus = eventImporterTasklet.importEvent();
        stepContribution.setExitStatus(exitStatus);
        return RepeatStatus.FINISHED;
    }
}
