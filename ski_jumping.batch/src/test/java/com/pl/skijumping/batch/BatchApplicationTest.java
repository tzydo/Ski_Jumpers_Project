package com.pl.skijumping.batch;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@EntityScan(basePackages = "com.pl.skijumping.domain.entity")
@EnableJpaRepositories(basePackages = "com.pl.skijumping.domain.*")
@SpringBootApplication(scanBasePackages = "com.pl.skijumping.*")
@ComponentScan(basePackages = "com.pl.skijumping")
public class BatchApplicationTest { }