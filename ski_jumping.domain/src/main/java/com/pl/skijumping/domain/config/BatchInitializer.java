//package com.pl.ski_jumping.config;
//
//import com.pl.skijumping.batchModel.CountryItemProcessor;
//import com.pl.skijumping.batchModel.SkiJumperItemProcessor;
//import com.pl.skijumping.entity.SkiJumper;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.step.tasklet.TaskletStep;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//@EnableBatchProcessing
//public class BatchInitializer {
//
//    @Autowired
//    public FlatFileItemReader skiJumperItemReader;
//
//    @Autowired
//    public FlatFileItemReader countryItemReader;
//
//    @Autowired
//    public SkiJumperItemProcessor skiJumperItemProcessor;
//
//    @Autowired
//    public CountryItemProcessor countryItemProcessor;
//
//    @Autowired
//    public JdbcBatchItemWriter skiJumperItemWriter;
//
//    @Autowired
//    public JdbcBatchItemWriter countryItemWriter;
//
//    @Autowired
//    public StepBuilderFactory stepBuilderFactory;
//
//    @Autowired
//    public JobBuilderFactory jobBuilderFactory;
//
//    @Bean
//    public TaskletStep step1(){
//        return stepBuilderFactory.get("loading skijumper list").<SkiJumper, SkiJumper>chunk(10)
//                .reader(skiJumperItemReader)
//                .processor(skiJumperItemProcessor)
//                .writer(skiJumperItemWriter)
//                .build();
//    }
//
//    @Bean
//    public TaskletStep step2(){
//        return stepBuilderFactory.get("loading countries").chunk(10)
//                .reader(countryItemReader)
//                .processor(countryItemProcessor)
//                .writer(countryItemWriter)
//                .build();
//    }
//
//    @Bean
//    public Job importUserJob() {
//        return jobBuilderFactory.get("import ski jumper list")
//                .incrementer(new RunIdIncrementer())
//                .flow(step1()) //insert to db
//                .next(step2())
//                .end()
//                .build();
//    }
//}
