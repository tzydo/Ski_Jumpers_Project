package com.pl.skijumping.service;

import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@EntityScan(basePackages = "com.pl.skijumping.domain.entity", basePackageClasses = {Jsr310JpaConverters.class})
@EnableJpaRepositories(basePackages = "com.pl.skijumping.domain.*")
@SpringBootApplication(scanBasePackages = "com.pl.skijumping.*")
@ComponentScan(basePackages = "com.pl.skijumping")
public class ApplicationTest {

    @Test
    public void test() {
        //
    }
}
