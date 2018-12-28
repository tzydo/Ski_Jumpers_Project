package com.pl.skijumping.batch.dataimportjob;

import com.pl.skijumping.batch.util.FileScannerConst;
import com.pl.skijumping.batch.util.SourceDownloader;
import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class DataImporterTasklet implements Tasklet {
    private final String host;
    private final String directory;
    private final DiagnosticMonitor diagnosticMonitor;
    private Integer monthToDownload;
    private final HtmlDownloader htmlDownloader;

    public DataImporterTasklet(String host,
                               String directory,
                               DiagnosticMonitor diagnosticMonitor,
                               Integer monthToDownload,
                               HtmlDownloader htmlDownloader) {
        this.host = host;
        this.directory = directory;
        this.diagnosticMonitor = diagnosticMonitor;
        this.monthToDownload = monthToDownload;
        this.htmlDownloader = htmlDownloader;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws IOException {
        if (monthToDownload == null || monthToDownload > DataImporterConst.MAX_PREVIOUS_MONTH) {
            diagnosticMonitor.logWarn("No set count of previous months to download");
            monthToDownload = DataImporterConst.DEFAULT_MONTH_TO_DOWNLOAD;
        }

        SourceDownloader sourceDownloader = new SourceDownloader(directory, diagnosticMonitor, htmlDownloader);
        LocalDate localDate = LocalDate.now();
        List<String> generatedURL = DataImporterUtil.generateSeasonCodeByPreviousMonths(localDate.getYear(), localDate.getMonthValue(), monthToDownload, host);

        Set existingFileNamesSet = FileUtil.getFilesName(FileUtil.getPathList(FileUtil.getPath(FileUtil.getResource(), directory)));

        for (String url : generatedURL) {
            String fileNameFromHost = DataImporterUtil.getFileNameFromHost(url);
            if (existingFileNamesSet.contains(FileScannerConst.FILE_DATA_IMPORT + fileNameFromHost)) {
                continue;
            }
            ExitStatus exitStatus = sourceDownloader.download(FileScannerConst.FILE_DATA_IMPORT, url, fileNameFromHost);
            stepContribution.setExitStatus(exitStatus);
            if (ExitStatus.FAILED.getExitCode().equals(exitStatus.getExitCode())) {
                diagnosticMonitor.logError(String.format("Error during download source from:  %s", url), getClass());
                break;
            }
        }
        return RepeatStatus.FINISHED;
    }
}
