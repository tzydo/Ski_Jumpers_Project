package com.pl.skijumping.batch.dataimportjob.scheduler;

import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.pl.skijumping.batch.dataimportjob.configuration.DataImporter.DATA_IMPORT_JOB_NAME;

@Component
public class DataImportScheduler {

    private final JobLauncher jobLauncher;
    private final Job dataImportJob;
    private Boolean isEnable;
    private final DiagnosticMonitor diagnosticMonitor;

    public DataImportScheduler(JobLauncher jobLauncher,
                               @Qualifier(DATA_IMPORT_JOB_NAME)Job dataImportJob,
                               @Value("${skijumping.settings.scheduler.importData.enable}")Boolean isEnable,
                               DiagnosticMonitor diagnosticMonitor) {
        this.jobLauncher = jobLauncher;
        this.dataImportJob = dataImportJob;
        this.isEnable = isEnable;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Scheduled(cron = "${skijumping.settings.scheduler.importData.cron}")
    public JobExecution importData() throws InternalServiceException {
        if(!isEnable) {
            return null;
        }
        isEnable = false;
        JobExecution jobExecution;
        try {
            diagnosticMonitor.logInfo(String.format("Starting job: %s", DATA_IMPORT_JOB_NAME));
            jobExecution = jobLauncher.run(dataImportJob, new JobParameters());
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            diagnosticMonitor.logError(String.format("Error during job %s", DATA_IMPORT_JOB_NAME), this.getClass());
            throw new InternalServiceException(
                    String.format("An error occurred while %s job", DATA_IMPORT_JOB_NAME), e);
        }

        diagnosticMonitor.logInfo(String.format("Finish successfully job %s", DATA_IMPORT_JOB_NAME));
        isEnable = true;
        return jobExecution;
    }
}
