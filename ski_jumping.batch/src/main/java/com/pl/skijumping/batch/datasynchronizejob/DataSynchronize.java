//package com.pl.skijumping.batch.datasynchronizejob;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DataSynchronize {
//    public static final String DATA_SYNCHRONIZE_JOB = "Data_Synchronize_Job";
//    public static final String DATA_SYNCHRONIZE_STEP_NAME = "Data_Synchronize_Step";
//    private final String filePath;
//    private final JobBuilderFactory jobBuilder;
//    private final StepBuilderFactory stepBuilder;
//
//
//    public DataSynchronize(@Value("${skijumping.settings.scheduler.synchronize.enable}") String filePath,
//                           JobBuilderFactory jobBuilder,
//                           StepBuilderFactory stepBuilder) {
//        this.filePath = filePath;
//        this.jobBuilder = jobBuilder;
//        this.stepBuilder = stepBuilder;
//    }
//
//    @Bean(name = DATA_SYNCHRONIZE_JOB)
//    public Job dataSynchronizeJob() {
//        return jobBuilder.get(DATA_SYNCHRONIZE_JOB)
//                .start(dataSynchronizeStep())
//                .build();
//    }
//
//
//
//    @Bean
//    @StepScope
//    public Step dataSynchronizeStep() {
//        return stepBuilder.get(DATA_SYNCHRONIZE_STEP_NAME)
//                .chunk(1)
//                .reader(dataSynchronizeReader())
//                .processor(dataSynchronizeProcessor())
//                .writer(dataSynchronizeWriter())
//                .build();
//    }
//
//    private ItemReader<?> dataSynchronizeReader() {
//
//    }
//
//    private ItemWriter<? super Object> dataSynchronizeWriter() {
//    }
//
//    private ItemProcessor<? super Object,?> dataSynchronizeProcessor() {
//    }
//
//}
