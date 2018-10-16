package com.pl.skijumping.liquibase;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfiguration {

    private static final String TEST_PROFILE = "test";

    private final Environment environment;
    private final DataSource dataSource;

    public LiquibaseConfiguration(Environment environment, DataSource dataSource) {
        this.environment = environment;
        this.dataSource = dataSource;
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:/db/changelog/changelog-master.xml");
        liquibase.setDataSource(dataSource);
        if (environment.getActiveProfiles().equals(TEST_PROFILE)) {
            liquibase.setDropFirst(true);
        } else {
            liquibase.setDropFirst(false);
        }
        return liquibase;
    }
}
