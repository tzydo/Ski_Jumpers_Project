package com.pl.skijumping.batch.dataimportjob.scheduler;

import com.pl.skijumping.batch.BatchApplication;
import com.pl.skijumping.batch.BatchApplicationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.pl.skijumping.batch.dataimportjob.configuration.DataSynchronization.DATA_SYNCHRONIZATION_JOB_NAME;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = {BatchApplication.class, BatchApplicationTest.class})
@TestPropertySource(properties = {
        "skijumping.settings.host=http://www.fis-ski.com/ski-jumping/events-and-places/results/",
        "skijumping.settings.directory=testTMP",
        "skijumping.settings.fileName=testSkiJumper.txt",
        "skijumping.settings.scheduler.enable=true",
        "skijumping.settings.scheduler.cron= 0 0/20 * * * *"
})
public class DataSynchronizationSchedulerTest {
    @Autowired
    @Qualifier(DATA_SYNCHRONIZATION_JOB_NAME)
    private Job job;
    @Autowired
    private JobLauncher jobLauncherTestUtils;

    @Test
    public void synchronizeDataTest() throws Exception {
        DataSynchronizationScheduler dataSynchronizationScheduler =
                new DataSynchronizationScheduler(jobLauncherTestUtils, job, true);
        Assertions.assertThat(dataSynchronizationScheduler.synchronizeData()).isEqualTo(ExitStatus.COMPLETED);
    }
}