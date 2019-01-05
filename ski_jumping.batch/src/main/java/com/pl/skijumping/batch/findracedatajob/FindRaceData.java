//package com.pl.skijumping.batch.datareaderjob.jobs.findracedata;
//
//import com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor.FindRaceDataProcessorBatch;
//import com.pl.skijumping.batch.datareaderjob.jobs.findracedata.reader.DataReaderBatch;
//import com.pl.skijumping.batch.datareaderjob.jobs.findracedata.writer.FindRaceDataWriterBatch;
//import com.pl.skijumping.batch.listener.StepListener;
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//import com.pl.skijumping.dto.DataRaceDTO;
//import com.pl.skijumping.service.CompetitionNameService;
//import com.pl.skijumping.service.CompetitionTypeService;
//import com.pl.skijumping.service.DataRaceService;
//import com.pl.skijumping.service.mapper.CompetitionNameMapper;
//import com.pl.skijumping.service.mapper.CompetitionTypeMapper;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.StepExecutionListener;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemStreamReader;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class FindRaceData {
//    public static final String FIND_RACE_DATA_JOB_NAME = "Find_Race_Data_Job";
//    public static final String FIND_RACE_DATA_STEP_NAME = "Find_Race_Data_Step";
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//    private final String filePath;
//    private final CompetitionTypeService competitionTypeService;
//    private final CompetitionNameService competitionNameService;
//    private final DataRaceService dataRaceService;
//    private final DiagnosticMonitor diagnosticMonitor;
//    private final CompetitionNameMapper competitionNameMapper;
//    private final CompetitionTypeMapper competitionTypeMapper;
//
//    public FindRaceData(JobBuilderFactory jobBuilderFactory,
//                        StepBuilderFactory stepBuilderFactory,
//                        @Value("${skijumping.settings.directory}") String directoryPath,
//                        CompetitionTypeService competitionTypeService,
//                        CompetitionNameService competitionNameService,
//                        DataRaceService dataRaceService,
//                        DiagnosticMonitor diagnosticMonitor,
//                        CompetitionNameMapper competitionNameMapper,
//                        CompetitionTypeMapper competitionTypeMapper) {
//        this.jobBuilderFactory = jobBuilderFactory;
//        this.stepBuilderFactory = stepBuilderFactory;
//        this.filePath = null;
//        this.competitionTypeService = competitionTypeService;
//        this.competitionNameService = competitionNameService;
//        this.dataRaceService = dataRaceService;
//        this.diagnosticMonitor = diagnosticMonitor;
//        this.competitionNameMapper = competitionNameMapper;
//        this.competitionTypeMapper = competitionTypeMapper;
//    }
//
//    @Bean(name = FIND_RACE_DATA_JOB_NAME)
//    public Job findRaceDataJob() {
//        return jobBuilderFactory.get(FIND_RACE_DATA_JOB_NAME)
//                .start(findRaceDataStep())
//                .build();
//    }
//
//    @Bean(name = FIND_RACE_DATA_STEP_NAME)
//    public Step findRaceDataStep() {
//        return stepBuilderFactory.get(FIND_RACE_DATA_STEP_NAME)
//                .<String, List<DataRaceDTO>>chunk(1)
//                .reader(dataReaderBatch())
//                .processor(findRaceDataProcessorBatch())
//                .writer(findRaceDataWriterBatch())
//                .listener(stepListener())
//                .build();
//    }
//
//    @Bean
//    @StepScope
//    public ItemStreamReader<String> dataReaderBatch() {
//        return new DataReaderBatch(filePath, diagnosticMonitor);
//    }
//
//    @Bean
//    @StepScope
//    public ItemProcessor<String, List<DataRaceDTO>> findRaceDataProcessorBatch() {
//        return new FindRaceDataProcessorBatch(diagnosticMonitor);
//    }
//
//    @Bean
//    @StepScope
//    public FindRaceDataWriterBatch findRaceDataWriterBatch() {
//        return new FindRaceDataWriterBatch(
//                competitionTypeService, competitionNameService, dataRaceService, diagnosticMonitor, competitionNameMapper, competitionTypeMapper);
//    }
//
//    @Bean
//    public StepExecutionListener stepListener() {
//        return new StepListener();
//    }
//}
