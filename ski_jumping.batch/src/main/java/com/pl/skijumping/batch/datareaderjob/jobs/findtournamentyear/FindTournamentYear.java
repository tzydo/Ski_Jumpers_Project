package com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear;

import com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.processor.FindTournamentYearProcessor;
import com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.writer.FindTournamentYearWriter;
import com.pl.skijumping.batch.datareaderjob.reader.DataReaderBatch;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.service.TournamentYearService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FindTournamentYear {
    private static final String FIND_TOURNAMENT_YEAR_JOB_NAME = "Find_Tournament_Year_Job";
    private static final String FIND_TOURNAMENT_YEAR_STEP_NAME = "Find_Tournament_Year_Step";
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final TournamentYearService tournamentYearService;
    private final String filePath;
    private final DiagnosticMonitor diagnosticMonitor;

    public FindTournamentYear(JobBuilderFactory jobBuilderFactory,
                              StepBuilderFactory stepBuilderFactory,
                              @Value("${skijumping.settings.fileName}")String filePath,
                              TournamentYearService tournamentYearService,
                              DiagnosticMonitor diagnosticMonitor) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.filePath = filePath;
        this.tournamentYearService = tournamentYearService;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Bean(name = FIND_TOURNAMENT_YEAR_JOB_NAME)
    public Job findTournamentYearJob() {
        return jobBuilderFactory.get(FIND_TOURNAMENT_YEAR_JOB_NAME)
                .start(findTournamentYearStep())
                .build();
    }

    @Bean
    public Step findTournamentYearStep() {
        return stepBuilderFactory.get(FIND_TOURNAMENT_YEAR_STEP_NAME)
                .<String,List<String>>chunk(1)
                .reader(dataReaderBatch())
                .processor(findTournamentYearProcessor())
                .writer(findTournamentYearWriter())
                .build();
    }

    @Bean
    @StepScope
    public DataReaderBatch dataReaderBatch() {
        return new DataReaderBatch(this.filePath);
    }


    @Bean
    @StepScope
    public FindTournamentYearProcessor findTournamentYearProcessor() {
        return new FindTournamentYearProcessor(diagnosticMonitor);
    }

    @Bean
    @StepScope
    public FindTournamentYearWriter findTournamentYearWriter() {
        return new FindTournamentYearWriter(tournamentYearService, diagnosticMonitor);
    }
}
