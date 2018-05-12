package com.pl.skijumping.batch.dataimportjob.scheduler;

import com.pl.skijumping.batch.BatchApplication;
import com.pl.skijumping.batch.BatchApplicationTest;
import com.pl.skijumping.common.util.FileUtil;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.Optional;

import static com.pl.skijumping.batch.dataimportjob.configuration.DataImporter.DATA_IMPORT_JOB_NAME;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest(classes = BatchApplicationTest.class)
@DataJpaTest
//@ContextConfiguration(classes = BatchApplicationTest.class)
public class DataImporterSchedulerTest {
    @Autowired
    @Qualifier(DATA_IMPORT_JOB_NAME)
    private Job job;
    @Autowired
    private JobLauncher jobLauncherTestUtils;

    @Test
    public void importDataTest() throws Exception {
        DataImportScheduler dataImportScheduler =
                new DataImportScheduler(jobLauncherTestUtils, job, true);
        ExitStatus exitStatus = dataImportScheduler.importData().getExitStatus();
        Assertions.assertThat(exitStatus).isEqualTo(ExitStatus.COMPLETED);

        Optional<File> file = FileUtil.getFile("testTMP/testSkiJumper.txt");
        Assertions.assertThat(file.isPresent()).isTrue();
        Assertions.assertThat(file.get().isFile()).isTrue();
    }
}