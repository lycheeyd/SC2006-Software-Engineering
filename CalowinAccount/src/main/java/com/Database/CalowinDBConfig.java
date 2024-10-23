package com.Database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class CalowinDBConfig {

    @Bean(name = "CalowinDBEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean CalowinDBEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("CalowinDBDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.Account") // Entity package for CalowinDB
                .persistenceUnit("Profile")
                .build();
    }

    @Bean(name = "CalowinDBTransactionManager")
    public PlatformTransactionManager CalowinDBTransactionManager(
            @Qualifier("CalowinDBEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}