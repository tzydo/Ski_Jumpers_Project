package com.pl.skijumping.batch.datareaderjob.jobs.findracedata;

import com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor.FindRaceDataProcessorBatch;
import com.pl.skijumping.batch.datareaderjob.jobs.findracedata.reader.FindRaceDataReaderBatch;
import com.pl.skijumping.batch.datareaderjob.jobs.findracedata.writer.FindRaceDataWriterBatch;
import com.pl.skijumping.domain.dto.DataRaceDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindRaceData {
    private static final String FIND_RACE_DATA_YEAR_JOB_NAME = "Find_Race_Data_Job";
    private static final String FIND_RACE_DATA_STEP_NAME = "Find_Race_Data_Step";
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final String filePath;

    public FindRaceData(JobBuilderFactory jobBuilderFactory,
                              StepBuilderFactory stepBuilderFactory,
                              @Value("${skijumping.settings.fileName}")String filePath) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.filePath = filePath;
    }

    @Bean(name = FIND_RACE_DATA_YEAR_JOB_NAME)
    public Job findTournamentYearJob() {
        return jobBuilderFactory.get(FIND_RACE_DATA_YEAR_JOB_NAME)
                .start(findTournamentYearStep())
                .build();
    }

    @Bean
    public Step findTournamentYearStep() {
        return stepBuilderFactory.get(FIND_RACE_DATA_STEP_NAME)
                .<String,DataRaceDTO>chunk(1)
                .reader(findDataReaderBatch())
                .processor(findRaceDataProcessorBatch())
                .writer(findRaceDataWriterBatch())
                .build();
    }

    @Bean
    @StepScope
    public ItemStreamReader<String> findDataReaderBatch() {
        return new FindRaceDataReaderBatch(this.filePath);
    }


    @Bean
    @StepScope
    public ItemProcessor<String, DataRaceDTO> findRaceDataProcessorBatch() {
        return new FindRaceDataProcessorBatch();
    }

    @Bean
    @StepScope
    public FindRaceDataWriterBatch findRaceDataWriterBatch() {
        return new FindRaceDataWriterBatch();
    }
}
