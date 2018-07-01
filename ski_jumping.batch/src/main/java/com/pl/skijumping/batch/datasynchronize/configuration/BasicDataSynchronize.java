package com.pl.skijumping.batch.datasynchronize.configuration;

import com.pl.skijumping.batch.datasynchronize.BasicDataSynchronizeTasklet;
import com.pl.skijumping.batch.listener.StepListener;
import com.pl.skijumping.client.IHtmlDownloader;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.service.TournamentYearService;
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
public class BasicDataSynchronize {
    public static final String BASIC_DATA_SYNCHRONIZE_JOB_NAME = "Basic_Data_Synchronize_Job";
    public static final String BASIC_DATA_SYNCHRONIZE_STEP_NAME = "Basic_Data_Synchronize_Step";

    private final String host;
    private final String directory;
    private final JobBuilderFactory jobBuilder;
    private final StepBuilderFactory stepBuilder;
    private final DiagnosticMonitor diagnosticMonitor;
    private final TournamentYearService tournamentYearService;
    private final IHtmlDownloader htmlDownloader;

    public BasicDataSynchronize(@Value("${skijumping.settings.hostWithFilterForYear}") String host,
                                @Value("${skijumping.settings.directory}") String directory,
                                IHtmlDownloader htmlDownloader,
                                TournamentYearService tournamentYearService,
                                JobBuilderFactory jobBuilder,
                                StepBuilderFactory stepBuilder,
                                DiagnosticMonitor diagnosticMonitor) {
        this.host = host;
        this.directory = directory;
        this.tournamentYearService = tournamentYearService;
        this.jobBuilder = jobBuilder;
        this.stepBuilder = stepBuilder;
        this.htmlDownloader = htmlDownloader;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Bean(name = BASIC_DATA_SYNCHRONIZE_JOB_NAME)
    public Job basicDataSynchronizeJob() {
        return this.jobBuilder.get(BASIC_DATA_SYNCHRONIZE_JOB_NAME)
                .start(basicDataSynchronizeStep())
                .build();
    }

    @Bean(name = BASIC_DATA_SYNCHRONIZE_STEP_NAME)
    public Step basicDataSynchronizeStep() {
        return this.stepBuilder.get(BASIC_DATA_SYNCHRONIZE_STEP_NAME)
                .tasklet(basicDataImporterTasklet())
                .listener(stepExecutionListener())
                .build();
    }

    @Bean
    public Tasklet basicDataImporterTasklet() {
        return new BasicDataSynchronizeTasklet(host, directory, tournamentYearService, htmlDownloader, diagnosticMonitor);
    }

    @Bean
    public StepExecutionListener stepExecutionListener() {
        return new StepListener();
    }
}

