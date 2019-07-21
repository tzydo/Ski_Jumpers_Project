package com.pl.skijumping.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//@EntityScan(basePackageClasses = {Jsr310JpaConverters.class},
@EntityScan(basePackages = "com.pl.skijumping.domain.entity")
@SpringBootApplication(scanBasePackages = "com.pl.skijumping.*")
//@EnableJpaRepositories(basePackages = "com.pl.skijumping.domain.repository")
public class ApplicationRest {

    public static void main(String... args) {
        SpringApplication.run(ApplicationRest.class, args);
    }
}
