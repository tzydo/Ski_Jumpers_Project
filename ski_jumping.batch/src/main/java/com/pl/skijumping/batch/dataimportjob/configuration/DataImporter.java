package com.pl.skijumping.batch.dataimportjob.configuration;

import com.pl.skijumping.batch.dataimportjob.dataimport.DataImporterTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataImporter {
    public static final String DATA_IMPORT_JOB_NAME = "Data_Import_Job";
    private static final String DATA_IMPORTER_STEP_NAME = "Data_Importer_Step";

    private final String host;
    private final String directory;
    private final String fileName;
    private final JobBuilderFactory jobBuilder;
    private final StepBuilderFactory stepBuilder;

    public DataImporter(@Value("${skijumping.settings.host}") String host,
                        @Value("${skijumping.settings.directory}") String directory,
                        @Value("${skijumping.settings.fileName}") String fileName,
                        JobBuilderFactory jobBuilder,
                        StepBuilderFactory stepBuilder) {
        this.host = host;
        this.directory = directory;
        this.fileName = fileName;
        this.jobBuilder = jobBuilder;
        this.stepBuilder = stepBuilder;
    }

    @Bean(name = DATA_IMPORT_JOB_NAME)
    public Job dataImportJob() {
        return this.jobBuilder.get(DATA_IMPORT_JOB_NAME)
                .start(dataImporterStep())
                .build();
    }

    @Bean
    public Step dataImporterStep() {
        return this.stepBuilder.get(DATA_IMPORTER_STEP_NAME)
                .tasklet(dataImporterTasklet())
                .build();
    }

    @Bean
    public Tasklet dataImporterTasklet() {
        return new DataImporterTasklet( host, directory, fileName);
    }
}
