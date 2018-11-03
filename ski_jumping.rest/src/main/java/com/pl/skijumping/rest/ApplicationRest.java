package com.pl.skijumping.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackageClasses = {Jsr310JpaConverters.class},
        basePackages = "com.pl.skijumping.domain.entity")
@SpringBootApplication(scanBasePackages = "com.pl.skijumping.*")
@EnableJpaRepositories(basePackages = "com.pl.skijumping.domain.repository")
public class ApplicationRest {

    public static void main(String... args) {
        SpringApplication.run(ApplicationRest.class, args);
    }
}
