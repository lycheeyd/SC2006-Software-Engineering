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

    @Bean(name = "calowinDBEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean calowinDBEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("calowinDBDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.Account") // Entity package for CalowinDB
                .persistenceUnit("calowinDB")
                .build();
    }

    @Bean(name = "calowinDBTransactionManager")
    public PlatformTransactionManager calowinDBTransactionManager(
            @Qualifier("calowinDBEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}