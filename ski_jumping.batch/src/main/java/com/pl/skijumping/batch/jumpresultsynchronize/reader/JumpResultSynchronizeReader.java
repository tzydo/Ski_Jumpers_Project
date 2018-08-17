package com.pl.skijumping.batch.jumpresultsynchronize.reader;

import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;

import java.io.IOException;

class JumpResultSynchronizeReader {
    private final DiagnosticMonitor diagnosticMonitor;
    private final HtmlDownloader htmlDownloader;

    public JumpResultSynchronizeReader(DiagnosticMonitor diagnosticMonitor, HtmlDownloader htmlDownloader) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.htmlDownloader = htmlDownloader;
    }

    public String getResultData(String host) throws IOException, InternalServiceException {
        diagnosticMonitor.logInfo(String.format("Start downloading result data from host: %s", host));
        String source = htmlDownloader.downloadToString(host);

        if (source == null || source.isEmpty()) {
            diagnosticMonitor.logWarn(String.format("Downloaded source from host:%s is empty", host));
            return null;
        }

        return source;
    }
}
