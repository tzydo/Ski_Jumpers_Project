package com.pl.skijumping.batch.dataimportjob.scheduler;

import com.pl.skijumping.common.exception.InternalServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(DataImportScheduler.class);
    private final JobLauncher jobLauncher;
    private final Job dataImportJob;
    private Boolean isEnable;

    public DataImportScheduler(JobLauncher jobLauncher,
                               @Qualifier(DATA_IMPORT_JOB_NAME)Job dataImportJob,
                               @Value("${skijumping.settings.importData.enable}")Boolean isEnable) {
        this.jobLauncher = jobLauncher;
        this.dataImportJob = dataImportJob;
        this.isEnable = isEnable;
    }

    @Scheduled(cron = "${skijumping.settings.importData.cron}")
    public JobExecution importData() throws InternalServiceException {
        if(!isEnable) {
            return null;
        }
        isEnable = false;
        JobExecution jobExecution;
        try {
            LOGGER.info("Starting job: {}", DATA_IMPORT_JOB_NAME);
            jobExecution = jobLauncher.run(dataImportJob, new JobParameters());
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            LOGGER.error("Error during job {}", DATA_IMPORT_JOB_NAME);
            throw new InternalServiceException(
                    String.format("An error occurred while %s job", DATA_IMPORT_JOB_NAME), e);
        }

        LOGGER.info("Finish successfully job {}", DATA_IMPORT_JOB_NAME);
        isEnable = true;
        return jobExecution;
    }
}
