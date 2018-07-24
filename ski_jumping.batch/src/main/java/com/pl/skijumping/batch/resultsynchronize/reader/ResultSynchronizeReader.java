package com.pl.skijumping.batch.resultsynchronize.reader;

import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;

import java.io.IOException;

class ResultSynchronizeReader {
    private final DiagnosticMonitor diagnosticMonitor;

    public ResultSynchronizeReader(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    public String getResultData(String host) throws IOException, InternalServiceException {
        HtmlDownloader fileDownloader = new HtmlDownloader();
        diagnosticMonitor.logInfo(String.format("Start downloading result data from host: %s", host));
        String source = fileDownloader.downloadToString(host);
        if (source == null || source.isEmpty()) {
            diagnosticMonitor.logWarn(String.format("Downloaded source from host:%s is empty", host));
        }

        return source;
    }
}
