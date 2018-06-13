package com.pl.skijumping.batch.dataimportjob.dataimport;

import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DataImporterTaskletTest {
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
                "http://localhost:8080", null, "testFile.txt", diagnosticMonitor);
        RepeatStatus execute = dataImporterTasklet.execute(stepContribution, chunkContext);
        Assertions.assertThat(execute).isEqualTo(RepeatStatus.FINISHED);
        Assertions.assertThat(stepContribution.getExitStatus()).isEqualTo(ExitStatus.FAILED);
    }

    @Test
    public void executeWhenCannotConnectTOHost() throws Exception {
        StepContribution stepContribution = stepExecution.createStepContribution();
        DataImporterTasklet dataImporterTasklet = new DataImporterTasklet(
                "http://localhost:8080", "directory", "directory/testFile.txt", diagnosticMonitor);
        RepeatStatus execute = dataImporterTasklet.execute(stepContribution, chunkContext);
        Assertions.assertThat(execute).isEqualTo(RepeatStatus.FINISHED);
        Assertions.assertThat(stepContribution.getExitStatus()).isEqualTo(ExitStatus.FAILED);

        Optional<File> file = FileUtil.getFile("directory");
        FileUtils.deleteDirectory(file.get());
    }
}