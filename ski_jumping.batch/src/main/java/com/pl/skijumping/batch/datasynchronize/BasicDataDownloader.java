package com.pl.skijumping.batch.datasynchronize;

import com.pl.skijumping.client.IHtmlDownloader;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.service.TournamentYearService;
import org.springframework.batch.core.ExitStatus;

import java.io.File;
import java.io.IOException;
import java.util.Map;

class BasicDataDownloader {
    public static final String FILE_NAME = "ski_jumper_";
    private final TournamentYearService tournamentYearService;
    private final String hostWithYear;
    private final String directory;
    private final DiagnosticMonitor diagnosticMonitor;
    private final IHtmlDownloader htmlDownloader;
    private final Boolean loadAllData;
    private final Integer numberOfPreviousYear;

    public BasicDataDownloader(TournamentYearService tournamentYearService,
                               String hostWithYear,
                               String directory,
                               IHtmlDownloader htmlDownloader,
                               DiagnosticMonitor diagnosticMonitor,
                               Boolean loadAllData,
                               Integer numberOfPreviousYear) {
        this.tournamentYearService = tournamentYearService;
        this.hostWithYear = hostWithYear;
        this.directory = directory;
        this.htmlDownloader = htmlDownloader;
        this.diagnosticMonitor = diagnosticMonitor;
        this.loadAllData = loadAllData;
        this.numberOfPreviousYear = numberOfPreviousYear;
    }

    public ExitStatus download() {
        BasicDataHostGenerator basicDataHostGenerator = new BasicDataHostGenerator(tournamentYearService, loadAllData, numberOfPreviousYear);
        diagnosticMonitor.logInfo("Starting generate url to download");
        Map<String, String> generateUrl = basicDataHostGenerator.generate(hostWithYear);
        diagnosticMonitor.logInfo(String.format("Generated: %d to download", generateUrl.size()));

        if (generateUrl.isEmpty()) {
            return new ExitStatus(ExitStatus.FAILED.getExitCode(),
                    "Cannot read other data from empty tournament year");
        }

        generateUrl.forEach((k, v) -> {
            String filePath = String.format("%s%s%s%s%s", directory, File.separator, FILE_NAME, k, ".txt");
            if (FileUtil.createFile(filePath)) {
                downloadSource(filePath, v);
            }
            diagnosticMonitor.logWarn(String.format("Cannot create file %s", filePath));
        });

        return new ExitStatus(ExitStatus.COMPLETED.getExitCode(), "Finish successfully");
    }

    private void downloadSource(String filePath, String host) {
        try {
            htmlDownloader.downloadSource(filePath, host);
        } catch (IOException | InternalServiceException e) {
            String errorMessage = String.format("Cannot download file from host%s to filepath: %s", host, filePath);
            diagnosticMonitor.logError(errorMessage, getClass());
        }
    }
}
