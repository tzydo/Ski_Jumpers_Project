package com.pl.skijumping.batch.datareaderjob.jobs.findracedata;

import com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor.FindRaceDataProcessorBatch;
import com.pl.skijumping.batch.datareaderjob.jobs.findracedata.reader.FindRaceDataReaderBatch;
import com.pl.skijumping.batch.datareaderjob.jobs.findracedata.writer.FindRaceDataWriterBatch;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.service.CompetitionTypeService;
import com.pl.skijumping.service.DataRaceService;
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
    private final CompetitionTypeService competitionTypeService;
    private final DataRaceService dataRaceService;
    private final DiagnosticMonitor diagnosticMonitor;

    public FindRaceData(JobBuilderFactory jobBuilderFactory,
                        StepBuilderFactory stepBuilderFactory,
                        @Value("${skijumping.settings.fileName}") String filePath,
                        CompetitionTypeService competitionTypeService,
                        DataRaceService dataRaceService,
                        DiagnosticMonitor diagnosticMonitor) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.filePath = filePath;
        this.competitionTypeService = competitionTypeService;
        this.dataRaceService = dataRaceService;
        this.diagnosticMonitor = diagnosticMonitor;
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
                .<String, DataRaceDTO>chunk(1)
                .reader(findDataReaderBatch())
                .processor(findRaceDataProcessorBatch())
                .writer(findRaceDataWriterBatch())
                .build();
    }

    @Bean
    @StepScope
    public ItemStreamReader<String> findDataReaderBatch() {
        return new FindRaceDataReaderBatch(this.filePath, this.diagnosticMonitor);
    }

    @Bean
    @StepScope
    public ItemProcessor<String, DataRaceDTO> findRaceDataProcessorBatch() {
        return new FindRaceDataProcessorBatch(this.diagnosticMonitor);
    }

    @Bean
    @StepScope
    public FindRaceDataWriterBatch findRaceDataWriterBatch() {
        return new FindRaceDataWriterBatch(this.competitionTypeService, dataRaceService, diagnosticMonitor);
    }
}
