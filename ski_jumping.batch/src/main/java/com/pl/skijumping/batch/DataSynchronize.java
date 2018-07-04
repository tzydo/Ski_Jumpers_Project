package com.pl.skijumping.batch;

import com.pl.skijumping.batch.dataimportjob.configuration.DataImporter;
import com.pl.skijumping.batch.datareaderjob.jobs.findracedata.FindRaceData;
import com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.FindTournamentYear;
import com.pl.skijumping.batch.datasynchronize.configuration.BasicDataSynchronize;
import com.pl.skijumping.batch.decider.DataImportDecider;
import com.pl.skijumping.batch.decider.FindRaceDataDecider;
import com.pl.skijumping.batch.decider.FindTournamentYearDecider;
import com.pl.skijumping.batch.datasynchronize.FileDeleteTasklet;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSynchronize {
    public static final String DATA_SYNCHRONIZE_JOB = "Data_Synchronize_Job";
    public static final String DATA_SYNCHRONIZE_STEP_NAME = "Data_Synchronize_Step";
    public static final String DELETE_FILE_STEP = "delete_file_step";
    private final JobBuilderFactory jobBuilder;
    private final StepBuilderFactory stepBuilderFactory;
    private final Step dataImporterStep;
    private final DiagnosticMonitor diagnosticMonitor;
    private final Step findTournamentYearStep;
    private final Step basicDataImporterStep;
    private final Step basicDataSynchronizeStep;
    private final Step findRaceDataStep;

    public DataSynchronize(
            JobBuilderFactory jobBuilder,
            StepBuilderFactory stepBuilderFactory,
            DiagnosticMonitor diagnosticMonitor,
            @Qualifier(value = DataImporter.DATA_IMPORTER_STEP_NAME) Step dataImporterStep,
            @Qualifier(value = FindTournamentYear.FIND_TOURNAMENT_YEAR_STEP_NAME) Step findTournamentYearStep,
            @Qualifier(value = FindRaceData.FIND_RACE_DATA_STEP_NAME) Step findRaceDataStep,
            @Qualifier(value = BasicDataSynchronize.BASIC_DATA_IMPORTER_STEP_NAME) Step basicDataImporterStep,
            @Qualifier(value = BasicDataSynchronize.BASIC_DATA_SYNCHRONIZE_STEP_NAME) Step basicDataSynchronizeStep){
        this.jobBuilder = jobBuilder;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataImporterStep = dataImporterStep;
        this.diagnosticMonitor = diagnosticMonitor;
        this.findTournamentYearStep = findTournamentYearStep;
        this.findRaceDataStep = findRaceDataStep;
        this.basicDataImporterStep = basicDataImporterStep;
        this.basicDataSynchronizeStep = basicDataSynchronizeStep;
    }

    @Bean(name = DATA_SYNCHRONIZE_JOB)
    public Job dataSynchronizeJob() {
        return jobBuilder.get(DATA_SYNCHRONIZE_JOB)
                .start(dataImporterStep)
                .next(dataImportDecider())
                .from(dataImportDecider()).on(DataImportDecider.ERROR_DATA_IMPORT).end()
                .from(dataImportDecider()).on(DataImportDecider.COMPLETE_DATA_IMPORT).to(findTournamentYearStep)

                .from(findTournamentYearStep)
                .next(deleteFileStep())
                .next(findTournamentYearDecider())
                .on(FindTournamentYearDecider.FAILED_TOURNAMENT_YEAR_FINDING).end()
                .on(FindTournamentYearDecider.COMPLETE_TOURNAMENT_YEAR_FINDING).to(findRaceDataStep)

                .from(findRaceDataStep).next(findRaceDataDecider())
                .on(FindRaceDataDecider.ERROR_FIND_RACE_DATA).end()
                .on(FindRaceDataDecider.COMPLETE_FIND_RACE_DATA).to(basicDataImporterStep).next(basicDataSynchronizeStep)
                .end()
                .build();
    }

    @Bean
    public JobExecutionDecider dataImportDecider() {
        return new DataImportDecider();
    }

    @Bean
    public JobExecutionDecider findTournamentYearDecider() {
        return new FindTournamentYearDecider();
    }

    @Bean
    public JobExecutionDecider findRaceDataDecider() {
        return new FindRaceDataDecider();
    }

    @Bean
    public Step deleteFileStep() {
        return this.stepBuilderFactory.get(DELETE_FILE_STEP)
                .tasklet(deleteFileTasklet())
                .build();
    }

    @Bean
    public Tasklet deleteFileTasklet() {
        return new FileDeleteTasklet(this.diagnosticMonitor);
    }
}
