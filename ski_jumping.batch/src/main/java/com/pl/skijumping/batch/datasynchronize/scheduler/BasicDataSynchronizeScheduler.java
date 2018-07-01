package com.pl.skijumping.batch.datasynchronize.scheduler;

import com.pl.skijumping.batch.datasynchronize.configuration.BasicDataSynchronize;
import com.pl.skijumping.batch.util.JobRunner;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.pl.skijumping.batch.dataimportjob.configuration.DataImporter.DATA_IMPORT_JOB_NAME;

@Component
public class BasicDataSynchronizeScheduler {
    private final JobLauncher jobLauncher;
    private final Job basicDataSynchronizeJob;
    private Boolean isEnable;
    private final DiagnosticMonitor diagnosticMonitor;

    public BasicDataSynchronizeScheduler(JobLauncher jobLauncher,
                               @Qualifier(BasicDataSynchronize.BASIC_DATA_SYNCHRONIZE_JOB_NAME) Job basicDataSynchronizeJob,
                               @Value("${skijumping.settings.scheduler.basicDataSynchronize.enable}") Boolean isEnable,
                               DiagnosticMonitor diagnosticMonitor) {
        this.jobLauncher = jobLauncher;
        this.basicDataSynchronizeJob = basicDataSynchronizeJob;
        this.isEnable = isEnable;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Scheduled(cron = "${skijumping.settings.scheduler.basicDataSynchronize.cron}")
    public JobExecution importData() throws InternalServiceException {
        JobRunner jobRunner = new JobRunner(
                isEnable, diagnosticMonitor, jobLauncher, basicDataSynchronizeJob, DATA_IMPORT_JOB_NAME);
        return jobRunner.run();
    }
}
