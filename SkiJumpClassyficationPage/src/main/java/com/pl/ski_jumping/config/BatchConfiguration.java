package com.pl.ski_jumping.config;

import com.pl.ski_jumping.batchModel.CountryItemProcessor;
import com.pl.ski_jumping.mapper.CountryMapper;
import com.pl.ski_jumping.mapper.SkiJumperSetMapper;
import com.pl.ski_jumping.model.Country;
import com.pl.ski_jumping.model.SkiJumper;
import com.pl.ski_jumping.batchModel.SkiJumperItemProcessor;
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
    public FlatFileItemReader skiJumperItemReader() {
        FlatFileItemReader<SkiJumper> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource("skiJumper.csv"));
        itemReader.setLineMapper(new DefaultLineMapper<SkiJumper>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setDelimiter(";");
                setNames(new String[]
                        {"rank", "bib", "fis_code",
                                "name", "surname",
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
    public JdbcBatchItemWriter skiJumperItemWriter() {
        JdbcBatchItemWriter<SkiJumper> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("insert into jumper " +
                "(rank, bib, fis_code," +
                "name, surname, nationality, " +
                "first_jump, points_for_first_jump," +
                "second_jump , points_for_second_jump," +
                "total_points)" +
                "values( :rank, :bib, :fis_code," +
                ":name, :surname, :nationality, " +
                ":first_jump, :points_for_first_jump," +
                ":second_jump , :points_for_second_jump," +
                ":total_points)");
        writer.setDataSource(dataSource);

        return writer;
    }


    @Bean
    public SkiJumperItemProcessor skiJumperItemProcessor() {
        return new SkiJumperItemProcessor();
    }


    @Bean
    CountryItemProcessor countryItemProcessor(){return new CountryItemProcessor();}


    @Bean
    public FlatFileItemReader countryItemReader(){
        FlatFileItemReader<Country> countryReader = new FlatFileItemReader<>();
        countryReader.setResource(new ClassPathResource("countries.csv"));
        countryReader.setLineMapper(new DefaultLineMapper<Country>(){{
            setLineTokenizer(new DelimitedLineTokenizer(){{
                setNames(new String[]{"name"});
            }});
            setFieldSetMapper(new CountryMapper());
        }});

        return countryReader;
    }


    @Bean
    public JdbcBatchItemWriter countryItemWriter(){
        JdbcBatchItemWriter<Country> countryWriter = new JdbcBatchItemWriter<>();
        countryWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        countryWriter.setSql("insert into country (name) values (:name)");
        countryWriter.setDataSource(dataSource);

        return countryWriter;
    }
}
