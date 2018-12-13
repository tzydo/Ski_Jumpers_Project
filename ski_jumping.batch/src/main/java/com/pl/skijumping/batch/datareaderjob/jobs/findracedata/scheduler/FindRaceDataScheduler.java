//package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.scheduler;
//
//import com.pl.skijumping.batch.util.JobRunner;
//import com.pl.skijumping.common.exception.InternalServiceException;
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import static com.pl.skijumping.batch.datareaderjob.jobs.findracedata.FindRaceData.FIND_RACE_DATA_JOB_NAME;
//import static com.pl.skijumping.batch.datareaderjob.jobs.findracedata.FindRaceData.FIND_RACE_DATA_STEP_NAME;
//
//@Component
//public class FindRaceDataScheduler {
//    private final JobLauncher jobLauncher;
//    private final Job dataImportJob;
//    private Boolean isEnable;
//    private final DiagnosticMonitor diagnosticMonitor;
//
//    public FindRaceDataScheduler(JobLauncher jobLauncher,
//                                 @Qualifier(FIND_RACE_DATA_JOB_NAME) Job findTournamentYearJob,
//                                 @Value("${skijumping.settings.scheduler.findRaceData.enable}") Boolean isEnable,
//                                 DiagnosticMonitor diagnosticMonitor) {
//        this.jobLauncher = jobLauncher;
//        this.dataImportJob = findTournamentYearJob;
//        this.isEnable = isEnable;
//        this.diagnosticMonitor = diagnosticMonitor;
//    }
//
//    @Scheduled(cron = "${skijumping.settings.scheduler.findRaceData.cron}")
//    public JobExecution importData() throws InternalServiceException {
//        JobRunner jobRunner = new JobRunner(isEnable, diagnosticMonitor, jobLauncher, dataImportJob, FIND_RACE_DATA_STEP_NAME);
//        return jobRunner.run();
//    }
//}
