package com.pl.skijumping.batch.sourceimporterevent;

import com.pl.skijumping.batch.util.FilePreparation;
import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;

import java.io.IOException;
import java.nio.file.Path;

class SourceDownloader {
    private final DiagnosticMonitor diagnosticMonitor;
    private final HtmlDownloader htmlDownloader;

    SourceDownloader(DiagnosticMonitor diagnosticMonitor,
                            HtmlDownloader htmlDownloader) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.htmlDownloader = htmlDownloader;
    }

    Path download(String directory, String host, String fileName) {
        String errorMessage;

        Path directoryPath = FilePreparation.prepareDirectory(directory);
        if (directoryPath == null) {
            errorMessage = "Cannot download source to null directory";
            diagnosticMonitor.logError(errorMessage, this.getClass());
            return null;
        }

        if (host == null) {
            errorMessage = "Host cannot be null!";
            diagnosticMonitor.logError(errorMessage, this.getClass());
            return null;
        }

        diagnosticMonitor.logInfo("Start downloading html source");
        Path filePath = null;
        try {
            filePath = htmlDownloader.downloadSource(FilePreparation.prepareFile(fileName, directoryPath), host);
            diagnosticMonitor.logInfo("Finish download");
        } catch (IOException | InternalServiceException e) {
            errorMessage = "Cannot download source! \n " + e.getMessage();
            diagnosticMonitor.logError(errorMessage, this.getClass());
        }

        return filePath;
    }
}
