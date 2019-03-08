package com.pl.skijumping.liquibase;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfiguration {

    private static final String TEST_PROFILE = "test";

    private final String activeProfile;
    private final DataSource dataSource;

    public LiquibaseConfiguration(@Value("${spring.profiles.active}")String activeProfile,
                                  DataSource dataSource) {
        this.activeProfile = activeProfile;
        this.dataSource = dataSource;
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:/db/changelog/changelog-master.xml");
        liquibase.setDataSource(dataSource);
        if (activeProfile.equals(TEST_PROFILE)) {
            liquibase.setDropFirst(true);
        } else {
            liquibase.setDropFirst(false);
        }
        liquibase.setContexts(activeProfile);
        return liquibase;
    }
}
