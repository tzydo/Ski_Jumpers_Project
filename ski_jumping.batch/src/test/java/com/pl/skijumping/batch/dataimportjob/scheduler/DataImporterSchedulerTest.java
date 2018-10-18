package com.pl.skijumping.batch.dataimportjob.scheduler;

import com.pl.skijumping.batch.BatchApplicationTest;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
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

import java.io.File;
import java.util.Optional;

import static com.pl.skijumping.batch.dataimportjob.configuration.DataImporter.DATA_IMPORT_JOB_NAME;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest(classes = BatchApplicationTest.class)
public class DataImporterSchedulerTest {
    @Autowired
    @Qualifier(DATA_IMPORT_JOB_NAME)
    private Job job;
    @Autowired
    private JobLauncher jobLauncherTestUtils;
    @Autowired
    private DiagnosticMonitor diagnosticMonitor;

    @Test
    public void importDataTest() throws Exception {
        DataImportScheduler dataImportScheduler =
                new DataImportScheduler(jobLauncherTestUtils, job, true, diagnosticMonitor);
        ExitStatus exitStatus = dataImportScheduler.importData().getExitStatus();
        Assertions.assertThat(exitStatus).isEqualTo(ExitStatus.COMPLETED);

        Optional<File> file = FileUtil.getFile("tmp" + File.separator + "SkiJumping.txt");
        Assertions.assertThat(file.isPresent()).isTrue();
        Assertions.assertThat(file.get().isFile()).isTrue();
        file.get().delete();
    }

    @Test
    public void importDataWhenEnableFalseTest() throws InternalServiceException {
        DataImportScheduler dataImportScheduler = new DataImportScheduler(jobLauncherTestUtils, job, false, diagnosticMonitor);
        JobExecution jobExecution = dataImportScheduler.importData();
        Assertions.assertThat(jobExecution).isNull();
    }
}