package com.pl.skijumping.batch.dataimportjob.dataimport;

import com.pl.skijumping.batch.dataimportjob.DataImporterConst;
import com.pl.skijumping.batch.dataimportjob.DataImporterUtil;
import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

public class DataImporterTasklet implements Tasklet {
    private final String host;
    private final String directory;
    private final DiagnosticMonitor diagnosticMonitor;
    private Integer yearToDownload;
    private final HtmlDownloader htmlDownloader;

    public DataImporterTasklet(String host,
                               String directory,
                               DiagnosticMonitor diagnosticMonitor,
                               Integer yearToDownload,
                               HtmlDownloader htmlDownloader) {
        this.host = host;
        this.directory = directory;
        this.diagnosticMonitor = diagnosticMonitor;
        this.yearToDownload = yearToDownload;
        this.htmlDownloader = htmlDownloader;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        if (yearToDownload == null) {
            diagnosticMonitor.logWarn("Not set count of previous years to download");
            yearToDownload = DataImporterConst.DEFAULT_YEAR_TO_DOWNLOAD;
        }

        SourceDownloader sourceDownloader = new SourceDownloader(directory, diagnosticMonitor, htmlDownloader);
        List<String> generatedURL = DataImporterUtil.generateSeasonMonthAndCodeByPreviousYear(yearToDownload, host);

        for (String url : generatedURL) {
            ExitStatus exitStatus = sourceDownloader.download(url, DataImporterUtil.getFileNameFromHost(url));
            stepContribution.setExitStatus(exitStatus);
            if (ExitStatus.FAILED.getExitCode().equals(exitStatus.getExitCode())) {
                diagnosticMonitor.logError(String.format("Error during download source from:  %s", url), getClass());
                break;
            }
        }
        return RepeatStatus.FINISHED;
    }
}
