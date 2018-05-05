package com.pl.skijumping.batch.dataimportjob.scheduler;

import com.pl.skijumping.batch.BatchApplication;
import com.pl.skijumping.batch.BatchApplicationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.pl.skijumping.batch.dataimportjob.configuration.DataImporter.DATA_SYNCHRONIZE_JOB_NAME;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = {BatchApplication.class, BatchApplicationTest.class})
@TestPropertySource(properties = {
        "skijumping.settings.host=http://www.fis-ski.com/ski-jumping/events-and-places/results/",
        "skijumping.settings.directory=testTMP",
        "skijumping.settings.fileName=testTMP/testSkiJumper.txt",
        "skijumping.settings.scheduler.enable=true",
        "skijumping.settings.scheduler.cron= 0 0/20 * * * *"
})
public class DataImporterSchedulerTest {
    @Autowired
    @Qualifier(DATA_SYNCHRONIZE_JOB_NAME)
    private Job job;
    @Autowired
    private JobLauncher jobLauncherTestUtils;

    @Test
    public void importDataTest() throws Exception {
        DataSynchronizationScheduler dataSynchronizationScheduler =
                new DataSynchronizationScheduler(jobLauncherTestUtils, job, true);
        ExitStatus exitStatus = dataSynchronizationScheduler.synchronizeData().getExitStatus();
        Assertions.assertThat(exitStatus).isEqualTo(ExitStatus.COMPLETED);
    }
}