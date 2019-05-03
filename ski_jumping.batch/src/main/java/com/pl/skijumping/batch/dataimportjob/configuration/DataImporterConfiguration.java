package com.pl.skijumping.batch.dataimportjob.configuration;

import com.pl.skijumping.batch.dataimportjob.DataImporterListener;
import com.pl.skijumping.batch.dataimportjob.DataImporterTasklet;
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
public class DataImporterConfiguration {
    public static final String DATA_IMPORT_JOB_NAME = "Data_Import_Job";
    private static final String DATA_IMPORTER_STEP_NAME = "Data_Importer_Step";

    private final String host;
    private final String directory;
    private final JobBuilderFactory jobBuilder;
    private final StepBuilderFactory stepBuilder;
    private final DiagnosticMonitor diagnosticMonitor;
    private final Integer monthToDownload;

    public DataImporterConfiguration(@Value("${skijumping.settings.host}") String host,
                                     @Value("${skijumping.settings.directory}") String directory,
                                     @Value("${skijumping.settings.numberOfPreviousMonth}") Integer monthToDownload,
                                     JobBuilderFactory jobBuilder,
                                     StepBuilderFactory stepBuilder,
                                     DiagnosticMonitor diagnosticMonitor) {
        this.host = host;
        this.directory = directory;
        this.jobBuilder = jobBuilder;
        this.stepBuilder = stepBuilder;
        this.diagnosticMonitor = diagnosticMonitor;
        this.monthToDownload = monthToDownload;
    }

    @Bean(name = DATA_IMPORT_JOB_NAME)
    public Job dataImportJob() {
        return this.jobBuilder.get(DATA_IMPORT_JOB_NAME)
                .start(dataImporterStep())
                .build();
    }

    @Bean(name = DATA_IMPORTER_STEP_NAME)
    public Step dataImporterStep() {
        return this.stepBuilder.get(DATA_IMPORTER_STEP_NAME)
                .tasklet(dataImporterTasklet())
                .listener(stepExecutionListener())
                .build();
    }

    @Bean
    public Tasklet dataImporterTasklet() {
        return new DataImporterTasklet(host, directory, diagnosticMonitor, monthToDownload);
    }

    @Bean
    public StepExecutionListener stepExecutionListener() {
        return new DataImporterListener(diagnosticMonitor);
    }
}
