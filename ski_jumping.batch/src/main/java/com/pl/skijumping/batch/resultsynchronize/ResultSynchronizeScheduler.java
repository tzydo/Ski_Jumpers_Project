package com.pl.skijumping.batch.resultsynchronize;

import com.pl.skijumping.batch.resultsynchronize.configuration.ResultSynchronize;
import com.pl.skijumping.batch.util.JobRunner;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class ResultSynchronizeScheduler {
    private final JobLauncher jobLauncher;
    private final Job resultSynchronizeJob;
    private Boolean isEnable;
    private final DiagnosticMonitor diagnosticMonitor;

    public ResultSynchronizeScheduler(JobLauncher jobLauncher,
                                      @Qualifier(ResultSynchronize.RESULT_SYNCHRONIZE_JOB_NAME) Job resultSynchronizeJob,
                                      @Value("${skijumping.settings.scheduler.resultSynchronize.enable}") Boolean isEnable,
                                      DiagnosticMonitor diagnosticMonitor) {
        this.jobLauncher = jobLauncher;
        this.resultSynchronizeJob = resultSynchronizeJob;
        this.isEnable = isEnable;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Scheduled(cron = "${skijumping.settings.scheduler.resultSynchronize.cron}")
    public JobExecution importData() throws InternalServiceException {
        JobRunner jobRunner = new JobRunner(
                isEnable, diagnosticMonitor, jobLauncher, resultSynchronizeJob, ResultSynchronize.RESULT_SYNCHRONIZE_JOB_NAME);
        return jobRunner.run();
    }
}
