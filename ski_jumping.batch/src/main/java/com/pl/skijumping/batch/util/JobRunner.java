package com.pl.skijumping.batch.util;

import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

public class JobRunner {
    private Boolean isEnable;
    private final DiagnosticMonitor diagnosticMonitor;
    private final JobLauncher jobLauncher;
    private final Job job;
    private final String jobName;

    public JobRunner(Boolean isEnable,
                     DiagnosticMonitor diagnosticMonitor,
                     JobLauncher jobLauncher,
                     Job job, String jobName) {
        this.isEnable = isEnable;
        this.diagnosticMonitor = diagnosticMonitor;
        this.jobLauncher = jobLauncher;
        this.job = job;
        this.jobName = jobName;
    }

    public JobExecution run() throws InternalServiceException {
        if (!isEnable) {
            return null;
        }
        isEnable = false;
        JobExecution jobExecution;
        try {
            diagnosticMonitor.logInfo(String.format("Starting job: %s", jobName));
            JobParameters jobParameters = new JobParametersBuilder().addLong("time",System.currentTimeMillis()).toJobParameters();
            jobExecution = jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            diagnosticMonitor.logError(String.format("Error during job %s", jobName), this.getClass());
            throw new InternalServiceException(
                    String.format("An error occurred while %s job", jobName), e);
        }

        diagnosticMonitor.logInfo(String.format("Finish successfully job %s", jobName));
        isEnable = true;
        return jobExecution;
    }
}
