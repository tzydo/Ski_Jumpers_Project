package com.pl.skijumping.batch.datasynchronizejob;

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

@Component
public class DataSynchronizeScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSynchronizeScheduler.class);

    public static final String DATA_SYNCHRONIZE_JOB_NAME = "Data_Synchronize_Job";
    private final Job dataSynchronizationJob;
    private final JobLauncher jobLauncher;
    private Boolean isEnable;

    public DataSynchronizeScheduler(JobLauncher jobLauncher,
                                    @Qualifier(DATA_SYNCHRONIZE_JOB_NAME) Job dataSynchronizationJob,
                                    @Value("${skijumping.settings.scheduler.enable}") Boolean isEnable) {
        this.jobLauncher = jobLauncher;
        this.dataSynchronizationJob = dataSynchronizationJob;
        this.isEnable = isEnable;
    }

    @Scheduled(cron = "${skijumping.settings.scheduler.cron}")
    public JobExecution synchronizeData() throws InternalServiceException {
        JobExecution jobExecution;
        if (!isEnable) {
            return null;
        }
        isEnable = false;
        try {
            LOGGER.info("Starting job: {}", DATA_SYNCHRONIZE_JOB_NAME);
            jobExecution = jobLauncher.run(dataSynchronizationJob, new JobParameters());
        } catch (JobExecutionAlreadyRunningException |
                JobRestartException |
                JobInstanceAlreadyCompleteException |
                JobParametersInvalidException e) {
            LOGGER.error("Error during job {}", DATA_SYNCHRONIZE_JOB_NAME);
            throw new InternalServiceException(String.format("An error occurred while %s job", DATA_SYNCHRONIZE_JOB_NAME), e);
        }

        LOGGER.info("Finish successfully job {}", DATA_SYNCHRONIZE_JOB_NAME);
        isEnable = true;
        return jobExecution;
    }
}
