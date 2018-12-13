//package com.pl.skijumping.batch;
//
//import com.pl.skijumping.common.exception.InternalServiceException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.batch.core.*;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
//import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
//import org.springframework.batch.core.repository.JobRestartException;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class DataSynchronizeScheduler {
//    private static final Logger LOGGER = LoggerFactory.getLogger(DataSynchronizeScheduler.class);
//
//    public static final String DATA_SYNCHRONIZE_JOB_NAME = "Data_Synchronize_Job";
//    public static final String FILE_PATH = "file_path";
//    private final Job dataSynchronizationJob;
//    private final JobLauncher jobLauncher;
//    private Boolean isEnable;
//    private final String filePath;
//
//    public DataSynchronizeScheduler(JobLauncher jobLauncher,
//                                    @Qualifier(DataSynchronize.DATA_SYNCHRONIZE_JOB) Job dataSynchronizationJob,
//                                    @Value("${skijumping.settings.scheduler.synchronize.enable}") Boolean isEnable
////                                    @Value("${skijumping.settings.fileName}") String fileName) {
//                                    ) {
//        this.jobLauncher = jobLauncher;
//        this.dataSynchronizationJob = dataSynchronizationJob;
//        this.isEnable = isEnable;
//        this.filePath = null;
//    }
//
//    @Scheduled(cron = "${skijumping.settings.scheduler.synchronize.cron}")
//    public JobExecution synchronizeData() throws InternalServiceException {
//        JobExecution jobExecution;
//        if (!isEnable) {
//            return null;
//        }
//        isEnable = false;
//        try {
//            LOGGER.info("Starting job: {}", DATA_SYNCHRONIZE_JOB_NAME);
//
//            Map parameters = new HashMap<String, JobParameter>();
//            JobParameter jobParameter = new JobParameter(filePath);
//            parameters.put(FILE_PATH, jobParameter);
//
//            jobExecution = jobLauncher.run(dataSynchronizationJob,  new JobParameters(parameters));
//        } catch (JobExecutionAlreadyRunningException |
//                JobRestartException |
//                JobInstanceAlreadyCompleteException |
//                JobParametersInvalidException e) {
//            LOGGER.error("Error during job {}", DATA_SYNCHRONIZE_JOB_NAME);
//            throw new InternalServiceException(String.format("An error occurred while %s job", DATA_SYNCHRONIZE_JOB_NAME), e);
//        }
//
//        LOGGER.info("Finish successfully job {}", DATA_SYNCHRONIZE_JOB_NAME);
//        isEnable = true;
//        return jobExecution;
//    }
//}
