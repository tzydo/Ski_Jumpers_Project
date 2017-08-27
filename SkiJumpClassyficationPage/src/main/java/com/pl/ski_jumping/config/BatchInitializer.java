package com.pl.ski_jumping.config;

import com.pl.ski_jumping.model.SkiJumper;
import com.pl.ski_jumping.model.SkiJumperItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableBatchProcessing
public class BatchInitializer {

    @Autowired
    public FlatFileItemReader itemReader;

    @Autowired
    public SkiJumperItemProcessor itemProcessor;

    @Autowired
    public JdbcBatchItemWriter itemWriter;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Bean
    public TaskletStep step(){
        return stepBuilderFactory.get("step1").<SkiJumper, SkiJumper>chunk(10)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("import ski jumper list")
                .incrementer(new RunIdIncrementer())
                .flow(step()) //insert to db
                .end()
                .build();
    }
}
