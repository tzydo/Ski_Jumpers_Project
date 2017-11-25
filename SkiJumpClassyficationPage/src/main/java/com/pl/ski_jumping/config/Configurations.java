package com.pl.ski_jumping.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
public class Configurations {

    @Autowired
    Environment environment;


    @Bean
    public DataSource dataSource(){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(environment.getProperty("className"));
        basicDataSource.setUrl(environment.getProperty("jdbc.url"));
        basicDataSource.setUsername(environment.getProperty("jdbc.user"));
        basicDataSource.setPassword(environment.getProperty("jdbc.password"));

        return basicDataSource;
    }

    final Properties hibernateProperties(){
        final Properties hibProperties = new Properties();
        hibProperties.setProperty("hibernate.hbm2ddl.auto",environment.getProperty("hibernate.hbm2ddl.auto"));
        hibProperties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        hibProperties.setProperty("ski_jumping.globally_quoted_identified", "true");
        hibProperties.setProperty("hibernate.show_sql","false");

        return hibProperties;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean managerFactoryBean(){
        LocalContainerEntityManagerFactoryBean managerFactoryBean
                = new LocalContainerEntityManagerFactoryBean();

        managerFactoryBean.setDataSource(dataSource());
        managerFactoryBean.setPackagesToScan(new String[]{"com.pl.ski_jumping"});


        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
//        Vendora adapter
        managerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
//        Hibernate properties
        managerFactoryBean.setJpaProperties(hibernateProperties());

        return managerFactoryBean;
    }
}