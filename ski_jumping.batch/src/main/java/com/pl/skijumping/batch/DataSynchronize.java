package com.pl.skijumping.batch;

import com.pl.skijumping.batch.dataimportjob.configuration.DataImporter;
import com.pl.skijumping.batch.datareaderjob.jobs.findracedata.FindRaceData;
import com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.FindTournamentYear;
import com.pl.skijumping.batch.decider.DataImportDecider;
import com.pl.skijumping.batch.decider.FindTournamentYearDecider;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSynchronize {
    public static final String DATA_SYNCHRONIZE_JOB = "Data_Synchronize_Job";
    public static final String DATA_SYNCHRONIZE_STEP_NAME = "Data_Synchronize_Step";
    private final JobBuilderFactory jobBuilder;
    private final StepBuilderFactory stepBuilder;
    private final DiagnosticMonitor diagnosticMonitor;
    private final Step dataImporterStep;
    private final Step findTournamentYearStep;
    private final Step findRaceDataStep;

    public DataSynchronize(
            JobBuilderFactory jobBuilder,
            StepBuilderFactory stepBuilder,
            @Qualifier(value = DataImporter.DATA_IMPORTER_STEP_NAME) Step dataImporterStep,
            @Qualifier(value = FindTournamentYear.FIND_TOURNAMENT_YEAR_STEP_NAME) Step findTournamentYearStep,
            @Qualifier(value = FindRaceData.FIND_RACE_DATA_STEP_NAME) Step findRaceDataStep,
            DiagnosticMonitor diagnosticMonitor) {
        this.jobBuilder = jobBuilder;
        this.stepBuilder = stepBuilder;
        this.dataImporterStep = dataImporterStep;
        this.findTournamentYearStep = findTournamentYearStep;
        this.findRaceDataStep = findRaceDataStep;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Bean(name = DATA_SYNCHRONIZE_JOB)
    public Job dataSynchronizeJob() {
        return jobBuilder.get(DATA_SYNCHRONIZE_JOB)
                .start(dataImporterStep)
                .next(dataImportDecider())
                .from(dataImportDecider()).on(DataImportDecider.ERROR_DATA_IMPORT).end()
                .from(dataImportDecider()).on(DataImportDecider.COMPLETE_DATA_IMPORT).to(findTournamentYearStep)

                .from(findTournamentYearStep).next(findTournamentYearDecider())
                .on(FindTournamentYearDecider.FAILED_TOURNAMENT_YEAR_FINDING).end()
                .on(FindTournamentYearDecider.COMPLETE_TOURNAMENT_YEAR_FINDING).to(findRaceDataStep).end()

                .build();
    }

    @Bean
    public JobExecutionDecider dataImportDecider(){
        return new DataImportDecider();
    }

    @Bean
    public JobExecutionDecider findTournamentYearDecider() {
        return new FindTournamentYearDecider();
    }
}
