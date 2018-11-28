package com.pl.skijumping.batch.dataimportjob.dataimport;

import com.pl.skijumping.batch.dataimportjob.DataImporterUtil;
import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.Months;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DataImporterTaskletTest {

    private static final String DIRECTORY = "directory";
    private static final String LOCALHOST = "http://localhost:8080";
    private static final Integer YEAR_TO_DOWNLOAD = 2;

    @After
    public void tearDown() throws IOException {
        Path directoryPath = Paths.get(FileUtil.getResource(), "directory");
        List<File> fileList = FileUtil.getFiles(directoryPath);
        fileList.forEach(file -> file.delete());
        directoryPath.toFile().delete();
    }

    @Mock
    private DiagnosticMonitor diagnosticMonitor;
    @Mock
    private ChunkContext chunkContext;

    private StepExecution stepExecution = new StepExecution("systemCommandStep",
            new JobExecution(new JobInstance(1L, "systemCommandJob")
                    , 1L, new JobParameters(), "configurationName"));

    @Test
    public void executeWhenCannotCreateDirectoryTest() throws Exception {
        HtmlDownloader htmlDownloaderMock = Mockito.mock(HtmlDownloader.class);
        StepContribution stepContribution = stepExecution.createStepContribution();
        DataImporterTasklet dataImporterTasklet = new DataImporterTasklet(LOCALHOST, null, diagnosticMonitor, YEAR_TO_DOWNLOAD, htmlDownloaderMock);
        RepeatStatus execute = dataImporterTasklet.execute(stepContribution, chunkContext);
        Assertions.assertThat(execute).isEqualTo(RepeatStatus.FINISHED);
        Assertions.assertThat(stepContribution.getExitStatus().getExitCode()).isEqualTo(ExitStatus.FAILED.getExitCode());
    }

    @Test
    public void executeWhenCannotConnectToHostTest() throws Exception {
        HtmlDownloader htmlDownloaderMock = Mockito.mock(HtmlDownloader.class);
        Mockito.when(htmlDownloaderMock.downloadSource(Mockito.any(), Mockito.anyString())).thenThrow(new InternalServiceException("error"));

        StepContribution stepContribution = stepExecution.createStepContribution();
        DataImporterTasklet dataImporterTasklet = new DataImporterTasklet(LOCALHOST, DIRECTORY, diagnosticMonitor, YEAR_TO_DOWNLOAD, htmlDownloaderMock);
        RepeatStatus execute = dataImporterTasklet.execute(stepContribution, chunkContext);
        Assertions.assertThat(execute).isEqualTo(RepeatStatus.FINISHED);
        Assertions.assertThat(stepContribution.getExitStatus().getExitCode()).isEqualTo(ExitStatus.FAILED.getExitCode());
    }

    @Test
    public void executeTest() {
        HtmlDownloader htmlDownloaderMock = Mockito.mock(HtmlDownloader.class);
        StepContribution stepContribution = stepExecution.createStepContribution();
        DataImporterTasklet dataImporterTasklet = new DataImporterTasklet(LOCALHOST, DIRECTORY, diagnosticMonitor, YEAR_TO_DOWNLOAD, htmlDownloaderMock);
        RepeatStatus execute = dataImporterTasklet.execute(stepContribution, chunkContext);
        Assertions.assertThat(execute).isEqualTo(RepeatStatus.FINISHED);
        Assertions.assertThat(stepContribution.getExitStatus().getExitCode()).isEqualTo(ExitStatus.COMPLETED.getExitCode());
    }
}