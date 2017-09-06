package com.pl.ski_jumping.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
public class Configurations {

    @Autowired
    Environment environment;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(environment.getProperty("jdbc.className"));
        driverManagerDataSource.setUrl(environment.getProperty("jdbc.url"));
        driverManagerDataSource.setUsername(environment.getProperty("jdbc.user"));
        driverManagerDataSource.setPassword(environment.getProperty("jdbc.password"));

        return driverManagerDataSource;
    }

    final Properties hibernateProperties(){
        final Properties hibProperties = new Properties();
        hibProperties.setProperty("hibernate.hbm2ddl.auto",environment.getProperty("hibernate.hbm2ddl.auto"));
        hibProperties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        hibProperties.setProperty("hibernate.show_sql","false");

        return hibProperties;
    }

//
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
