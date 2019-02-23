package com.pl.skijumping.batch.eventimportjob;

import com.pl.skijumping.batch.eventimportjob.configuration.EventImporterConfiguration;
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

@Component
public class EventIdImporterScheduler {

    private final JobLauncher jobLauncher;
    private final Job dataImportJob;
    private Boolean isEnable;
    private final DiagnosticMonitor diagnosticMonitor;

    public EventIdImporterScheduler(JobLauncher jobLauncher,
                               @Qualifier(EventImporterConfiguration.EVENT_IMPORT_JOB_NAME) Job dataImportJob,
                               @Value("${skijumping.settings.scheduler.importEvent.enable}") Boolean isEnable,
                               DiagnosticMonitor diagnosticMonitor) {
        this.jobLauncher = jobLauncher;
        this.dataImportJob = dataImportJob;
        this.isEnable = isEnable;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Scheduled(cron = "${skijumping.settings.scheduler.importData.cron}")
    public JobExecution importEvent() throws InternalServiceException {
        JobRunner jobRunner = new JobRunner(
                isEnable, diagnosticMonitor, jobLauncher, dataImportJob, EventImporterConfiguration.EVENT_IMPORT_JOB_NAME);
        return jobRunner.run();
    }
}
