package com.pl.skijumping.batch.resultsynchronize.configuration;

import com.pl.skijumping.batch.resultsynchronize.processor.ResultSynchronizeProcessorBatch;
import com.pl.skijumping.batch.resultsynchronize.reader.ResultSynchronizeReaderBatch;
import com.pl.skijumping.batch.resultsynchronize.writer.ResultSynchronizeWriterBatch;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.service.DataRaceService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ResultSynchronize {
    public static final String RESULT_SYNCHRONIZE_JOB_NAME = "Result_Synchronize_Job";
    public static final String RESULT_SYNCHRONIZE_STEP_NAME = "Result_Synchronize_Step";

    private final String host;
    private final String directory;
    private final JobBuilderFactory jobBuilder;
    private final StepBuilderFactory stepBuilder;
    private final DiagnosticMonitor diagnosticMonitor;
    private final DataRaceService dataRaceService;

    public ResultSynchronize(@Value("${skijumping.settings.host}") String host,
                             @Value("${skijumping.settings.directory}") String directory,
                             JobBuilderFactory jobBuilder,
                             StepBuilderFactory stepBuilder,
                             DiagnosticMonitor diagnosticMonitor,
                             DataRaceService dataRaceService) {
        this.host = host;
        this.directory = directory;
        this.jobBuilder = jobBuilder;
        this.stepBuilder = stepBuilder;
        this.diagnosticMonitor = diagnosticMonitor;
        this.dataRaceService = dataRaceService;
    }

    @Bean(name = RESULT_SYNCHRONIZE_JOB_NAME)
    public Job dataImportJob() {
        return this.jobBuilder.get(RESULT_SYNCHRONIZE_JOB_NAME)
                .start(resultSynchronizeStep())
                .build();
    }

    @Bean(name = RESULT_SYNCHRONIZE_STEP_NAME)
    public Step resultSynchronizeStep() {
        return this.stepBuilder.get(RESULT_SYNCHRONIZE_STEP_NAME)
                .<String, List<Object>>chunk(1)
                .reader(resultSynchronizeReader())
                .processor(resultSynchronizeProcessor())
                .writer(findRaceDataWriterBatch())
                .build();
    }

    @Bean
    public ItemStreamReader resultSynchronizeReader() {
        return new ResultSynchronizeReaderBatch(dataRaceService, diagnosticMonitor, this.host);
    }

    @Bean
    public ItemProcessor resultSynchronizeProcessor() {
        return new ResultSynchronizeProcessorBatch(diagnosticMonitor);
    }

    @Bean
    public ItemWriter findRaceDataWriterBatch() {
        return new ResultSynchronizeWriterBatch(diagnosticMonitor);
    }
}
