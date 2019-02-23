package com.pl.skijumping.batch.eventimportjob.configuration;

import com.pl.skijumping.batch.eventimportjob.tasklet.EventIdImporterTaskletBatch;
import com.pl.skijumping.batch.listener.StepListener;
import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventImporterConfiguration {
    public static final String EVENT_IMPORT_JOB_NAME = "Event_Import_Job";
    private static final String EVENT_IMPORTER_STEP_NAME = "Event_Importer_Step";

    private final String host;
    private final String directory;
    private final JobBuilderFactory jobBuilder;
    private final StepBuilderFactory stepBuilder;
    private final DiagnosticMonitor diagnosticMonitor;
    private final HtmlDownloader htmlDownloader;

    public EventImporterConfiguration(@Value("${skijumping.settings.eventHost}") String host,
                                     @Value("${skijumping.settings.directory}") String directory,
                                     JobBuilderFactory jobBuilder,
                                     StepBuilderFactory stepBuilder,
                                     DiagnosticMonitor diagnosticMonitor,
                                     HtmlDownloader htmlDownloader) {
        this.host = host;
        this.directory = directory;
        this.jobBuilder = jobBuilder;
        this.stepBuilder = stepBuilder;
        this.diagnosticMonitor = diagnosticMonitor;
        this.htmlDownloader = htmlDownloader;
    }

    @Bean(name = EVENT_IMPORT_JOB_NAME)
    public Job eventImportJob() {
        return this.jobBuilder.get(EVENT_IMPORT_JOB_NAME)
                .start(eventImporterStep())
                .build();
    }

    @Bean(name = EVENT_IMPORTER_STEP_NAME)
    public Step eventImporterStep() {
        return this.stepBuilder.get(EVENT_IMPORTER_STEP_NAME)
                .tasklet(eventImporterTasklet())
                .listener(stepExecutionListener())
                .build();
    }

    @Bean
    public Tasklet eventImporterTasklet() {
        return new EventIdImporterTaskletBatch(htmlDownloader, directory, diagnosticMonitor, host);
    }

    @Bean
    public StepExecutionListener stepExecutionListener() {
        return new StepListener();
    }
}
