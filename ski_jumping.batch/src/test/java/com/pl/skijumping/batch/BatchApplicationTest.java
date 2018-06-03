package com.pl.skijumping.batch;

import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@EntityScan(basePackages = "com.pl.skijumping.domain.entity")
@EnableJpaRepositories(basePackages = "com.pl.skijumping.domain.*")
@SpringBootApplication(scanBasePackages = "com.pl.skijumping.*")
@ComponentScan(basePackages = "com.pl.skijumping")
public class BatchApplicationTest {
    @Test
    public void test() {

    }
}