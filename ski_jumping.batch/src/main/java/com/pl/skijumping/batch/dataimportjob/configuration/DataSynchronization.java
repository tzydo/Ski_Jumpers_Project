package com.pl.skijumping.batch.dataimportjob.configuration;

import com.pl.skijumping.batch.dataimportjob.dataimport.processor.DataImporterProcessorBatch;
import com.pl.skijumping.batch.dataimportjob.dataimport.reader.DataImporterReaderBatch;
import com.pl.skijumping.batch.dataimportjob.dataimport.writer.DataImporterWriterBatch;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSynchronization {
    public static final String DATA_SYNCHRONIZATION_JOB_NAME = "Data_Synchronization_Job";
    private static final String DATA_IMPORTER_STEP_NAME = "Data_Importer_Step";

    private final String host;
    private final String directory;
    private final String fileName;
    private final JobBuilderFactory jobBuilder;
    private final StepBuilderFactory stepBuilder;


    public DataSynchronization(@Value("${skijumping.settings.host}") String host,
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


    @Bean(name = DATA_SYNCHRONIZATION_JOB_NAME)
    public Job dataSynchronizationJob() {
        return this.jobBuilder.get(DATA_SYNCHRONIZATION_JOB_NAME)
                .start(dataSynchronizationStep())
                .build();
    }

    @Bean
    public Step dataSynchronizationStep() {
        return this.stepBuilder.get(DATA_IMPORTER_STEP_NAME)
                .<Boolean, String>chunk(1)
                .reader(dataImporterReader())
                .processor(dataImporterProcessor())
                .writer(dataImporterWriter())
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<Boolean> dataImporterReader() {
        return new DataImporterReaderBatch(this.directory, this.fileName);
    }

    @Bean
    @StepScope
    public ItemProcessor<Boolean, String> dataImporterProcessor() {
        return new DataImporterProcessorBatch(this.host, this.fileName);
    }

    @Bean
    @StepScope
    public ItemWriter dataImporterWriter() {
        return new DataImporterWriterBatch();
    }

}
