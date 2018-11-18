package com.pl.skijumping.batch.dataimportjob.scheduler;

import com.pl.skijumping.batch.BatchApplicationTest;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static com.pl.skijumping.batch.dataimportjob.configuration.DataImporter.DATA_IMPORT_JOB_NAME;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest(classes = BatchApplicationTest.class)
public class DataImportSchedulerTest {
    @Autowired
    @Qualifier(DATA_IMPORT_JOB_NAME)
    private Job job;
    @Autowired
    private JobLauncher jobLauncherTestUtils;
    @Autowired
    private DiagnosticMonitor diagnosticMonitor;

    @After
    public void tearDown() throws IOException {
        FileUtil.getPath(FileUtil.getResource(), "tmp", "SkiJumping.txt").toFile().delete();
        FileUtil.getPath(FileUtil.getResource(), "tmp").toFile().delete();
    }

    @Test
    public void importDataTest() throws Exception {
        DataImportScheduler dataImportScheduler =
                new DataImportScheduler(jobLauncherTestUtils, job, true, diagnosticMonitor);
        ExitStatus exitStatus = dataImportScheduler.importData().getExitStatus();
        Assertions.assertThat(exitStatus).isEqualTo(ExitStatus.COMPLETED);

        Path pathToFile = FileUtil.getPath(FileUtil.getResource(), "tmp", "SkiJumping.txt");
        Assertions.assertThat(pathToFile.toFile().exists()).isTrue();
        Assertions.assertThat(pathToFile.toFile().isFile()).isTrue();
    }

    @Test
    public void importDataWhenExistTest() throws Exception {
        Path directory = FileUtil.createDirectory("tmp");
        FileUtil.createFile(directory, "SkiJumping.txt");

        DataImportScheduler dataImportScheduler =
                new DataImportScheduler(jobLauncherTestUtils, job, true, diagnosticMonitor);
        ExitStatus exitStatus = dataImportScheduler.importData().getExitStatus();
        Assertions.assertThat(exitStatus).isEqualTo(ExitStatus.COMPLETED);

        Path pathToFile = FileUtil.getPath(FileUtil.getResource(), "tmp", "SkiJumping.txt");
        Assertions.assertThat(pathToFile.toFile().exists()).isTrue();
        Assertions.assertThat(pathToFile.toFile().isFile()).isTrue();

        BufferedReader reader = Files.newBufferedReader(pathToFile);
        List<String> readLinesList = reader.lines().collect(Collectors.toList());
        reader.close();
        Assertions.assertThat(readLinesList.isEmpty()).isFalse();
        Assertions.assertThat(readLinesList.size()).isGreaterThan(1);
    }

    @Test
    public void importDataWhenEnableFalseTest() throws InternalServiceException {
        DataImportScheduler dataImportScheduler = new DataImportScheduler(jobLauncherTestUtils, job, false, diagnosticMonitor);
        JobExecution jobExecution = dataImportScheduler.importData();
        Assertions.assertThat(jobExecution).isNull();
    }
}