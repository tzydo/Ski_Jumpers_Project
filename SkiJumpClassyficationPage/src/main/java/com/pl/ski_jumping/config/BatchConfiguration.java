package com.pl.ski_jumping.config;

import com.pl.ski_jumping.mapper.SkiJumperSetMapper;
import com.pl.ski_jumping.model.SkiJumper;
import com.pl.ski_jumping.model.SkiJumperItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration {

    @Qualifier("dataSource")
    @Autowired
    public DataSource dataSource;

    @Bean
    public FlatFileItemReader itemReader(){
        FlatFileItemReader<SkiJumper> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource("skiJumper.csv"));
        itemReader.setLineMapper(new DefaultLineMapper<SkiJumper>(){{
            setLineTokenizer(new DelimitedLineTokenizer(){{
                setDelimiter(";");
                setNames(new String[]
                        {"rank", "bib", "fis_code",
                                "name", "lastName",
                                "nationality", "first_jump",
                                "points_for_first_jump",
                                "second_jump", "points_for_second_jump",
                                "total_points"});
            }});
                setFieldSetMapper(new SkiJumperSetMapper());
        }});
        return itemReader;
    }

    @Bean
    public JdbcBatchItemWriter itemWriter() {
        JdbcBatchItemWriter<SkiJumper> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("insert into jumper " +
                "(rank, bib, fis_code," +
                "name, lastName, nationality, " +
                "first_jump, points_for_first_jump," +
                "second_jump , points_for_second_jump," +
                "total_points)" +
                "values( :rank, :bib, :fis_code," +
                ":name, :lastName, :nationality, " +
                ":first_jump, :points_for_first_jump," +
                ":second_jump , :points_for_second_jump," +
                ":total_points)");
        writer.setDataSource(dataSource);

        return writer;
    }


    @Bean
    public SkiJumperItemProcessor itemProcessor(){
        return new SkiJumperItemProcessor();
    }
}
