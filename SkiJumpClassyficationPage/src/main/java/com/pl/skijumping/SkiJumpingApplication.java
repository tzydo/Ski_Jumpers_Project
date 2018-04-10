package com.pl.skijumping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.pl.skijumping.*")
@EnableJpaRepositories()
@EntityScan("com.pl.skijumping.entity")
public class SkiJumpingApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkiJumpingApplication.class, args);
    }
}
