package com.pl.skijumping.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableBatchProcessing
@EntityScan(basePackageClasses = {BatchApplication.class, Jsr310JpaConverters.class})
@SpringBootApplication(scanBasePackages = "com.pl.skijumping.*")
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.pl.skijumping.domain.repository")
public class BatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }
}