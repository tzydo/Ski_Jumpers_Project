package com.pl.skijumping.batch.jumpresultsynchronize.configuration;

import com.pl.skijumping.batch.jumpresultsynchronize.processor.JumpResultSynchronizeProcessorBatch;
import com.pl.skijumping.batch.jumpresultsynchronize.reader.JumpResultSynchronizeReaderBatch;
import com.pl.skijumping.batch.jumpresultsynchronize.writer.JumpResultSynchronizeWriterBatch;
import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.common.util.Pair;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.service.DataRaceService;
import com.pl.skijumping.service.JumpResultService;
import com.pl.skijumping.service.SkiJumperService;
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
public class JumpResultSynchronize {
    public static final String JUMP_RESULT_SYNCHRONIZE_JOB_NAME = "Jump_Result_Synchronize_Job";
    public static final String JUMP_RESULT_SYNCHRONIZE_STEP_NAME = "Jump_Result_Synchronize_Step";

    private final String host;
    private final HtmlDownloader htmlDownloader;
    private final JobBuilderFactory jobBuilder;
    private final StepBuilderFactory stepBuilder;
    private final DiagnosticMonitor diagnosticMonitor;
    private final DataRaceService dataRaceService;
    private final SkiJumperService skiJumperService;
    private final JumpResultService jumpResultService;

    public JumpResultSynchronize(@Value("${skijumping.settings.skiJumpingResultsHost}") String host,
                                 JobBuilderFactory jobBuilder,
                                 StepBuilderFactory stepBuilder,
                                 DiagnosticMonitor diagnosticMonitor,
                                 DataRaceService dataRaceService,
                                 SkiJumperService skiJumperService,
                                 JumpResultService jumpResultService,
                                 HtmlDownloader htmlDownloader) {
        this.host = host;
        this.jobBuilder = jobBuilder;
        this.stepBuilder = stepBuilder;
        this.diagnosticMonitor = diagnosticMonitor;
        this.dataRaceService = dataRaceService;
        this.skiJumperService = skiJumperService;
        this.jumpResultService = jumpResultService;
        this.htmlDownloader = htmlDownloader;
    }

    @Bean(name = JUMP_RESULT_SYNCHRONIZE_JOB_NAME)
    public Job dataImportJob() {
        return this.jobBuilder.get(JUMP_RESULT_SYNCHRONIZE_JOB_NAME)
                .start(resultSynchronizeStep())
                .build();
    }

    @Bean(name = JUMP_RESULT_SYNCHRONIZE_STEP_NAME)
    public Step resultSynchronizeStep() {
        return this.stepBuilder.get(JUMP_RESULT_SYNCHRONIZE_STEP_NAME)
                .<Pair<Long, Object>, List<JumpResultDTO>>chunk(1)
                .reader(resultSynchronizeReader())
                .processor(resultSynchronizeProcessor())
                .writer(resultSynchronizeWriter())
                .build();
    }

    @Bean
    public ItemStreamReader resultSynchronizeReader() {
        return new JumpResultSynchronizeReaderBatch(dataRaceService, diagnosticMonitor, htmlDownloader, host);
    }

    @Bean
    public ItemProcessor resultSynchronizeProcessor() {
        return new JumpResultSynchronizeProcessorBatch(diagnosticMonitor, skiJumperService);
    }

    @Bean
    public ItemWriter resultSynchronizeWriter() {
        return new JumpResultSynchronizeWriterBatch(jumpResultService);
    }
}
