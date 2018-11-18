package com.pl.skijumping.batch.dataimportjob.dataimport;

import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DataImporterTaskletTest {

    private static final String DIRECTORY = "directory";
    private static final String TEST_FILE_TXT = "testFile.txt";
    private static final String LOCALHOST = "http://localhost:8080";

    @After
    public void tearDown() throws IOException {
        FileUtil.getPath(FileUtil.getResource(), "directory", "testFile.txt").toFile().delete();
        FileUtil.getPath(FileUtil.getResource(), "directory").toFile().delete();
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
        StepContribution stepContribution = stepExecution.createStepContribution();
        DataImporterTasklet dataImporterTasklet = new DataImporterTasklet(
                LOCALHOST, null, TEST_FILE_TXT, diagnosticMonitor);
        RepeatStatus execute = dataImporterTasklet.execute(stepContribution, chunkContext);
        Assertions.assertThat(execute).isEqualTo(RepeatStatus.FINISHED);
        Assertions.assertThat(stepContribution.getExitStatus().getExitCode()).isEqualTo(ExitStatus.FAILED.getExitCode());
    }

    @Test
    public void executeWhenCannotConnectToHostTest() throws Exception {
        StepContribution stepContribution = stepExecution.createStepContribution();
        DataImporterTasklet dataImporterTasklet = new DataImporterTasklet(
                LOCALHOST, DIRECTORY, TEST_FILE_TXT, diagnosticMonitor);
        RepeatStatus execute = dataImporterTasklet.execute(stepContribution, chunkContext);
        Assertions.assertThat(execute).isEqualTo(RepeatStatus.FINISHED);
        Assertions.assertThat(stepContribution.getExitStatus().getExitCode()).isEqualTo(ExitStatus.FAILED.getExitCode());
    }

    @Test
    public void executeTest() throws Exception {
        String host = "https://www.fis-ski.com/DB/ski-jumping/calendar-results.html";
        StepContribution stepContribution = stepExecution.createStepContribution();
        DataImporterTasklet dataImporterTasklet = new DataImporterTasklet(
                host, DIRECTORY, TEST_FILE_TXT, diagnosticMonitor);
        RepeatStatus execute = dataImporterTasklet.execute(stepContribution, chunkContext);
        Assertions.assertThat(execute).isEqualTo(RepeatStatus.FINISHED);
        Assertions.assertThat(stepContribution.getExitStatus().getExitCode())
                .isEqualTo(ExitStatus.COMPLETED.getExitCode());
    }
}