package com.pl.skijumping.batch.dataimportjob.dataimport;

import com.pl.skijumping.batch.dataimportjob.DataImporterUtil;
import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.core.ExitStatus;

import java.io.IOException;
import java.nio.file.Path;

public class SourceDownloader {
    private final String directory;
    private final DiagnosticMonitor diagnosticMonitor;
    private final HtmlDownloader htmlDownloader;

    public SourceDownloader(String directory,
                            DiagnosticMonitor diagnosticMonitor,
                            HtmlDownloader htmlDownloader) {
        this.directory = directory;
        this.diagnosticMonitor = diagnosticMonitor;
        this.htmlDownloader = htmlDownloader;
    }

    public ExitStatus download(String host, String fileName) {
        String errorMessage;

        Path directoryPath = FilePreparation.prepareDirectory(directory);
        if (directoryPath == null) {
            errorMessage = "Cannot download source to null directory";
            diagnosticMonitor.logError(errorMessage, this.getClass());
            return new ExitStatus(ExitStatus.FAILED.getExitCode(), errorMessage);
        }

        if (host == null) {
            errorMessage = "Host cannot be null!";
            diagnosticMonitor.logError(errorMessage, this.getClass());
            return new ExitStatus(ExitStatus.FAILED.getExitCode(), errorMessage);
        }

        diagnosticMonitor.logInfo("Start downloading html source");

        try {
            Path filePath = FilePreparation.prepareFile(fileName, directoryPath);
            htmlDownloader.downloadSource(filePath, host);
        } catch (IOException | InternalServiceException e) {
            errorMessage = "Cannot download source! \n " + e.getMessage();
            diagnosticMonitor.logError(errorMessage, this.getClass());
            return new ExitStatus(ExitStatus.FAILED.getExitCode(), errorMessage);
        }

        diagnosticMonitor.logInfo("Finish download");
        diagnosticMonitor.logInfo("Job data import successfully finished");
        return new ExitStatus(ExitStatus.COMPLETED.getExitCode());
    }
}
