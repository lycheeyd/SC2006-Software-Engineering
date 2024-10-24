package com.Database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement

public class CalowinSecureDBConfig {

    @Primary
    @Bean(name = "calowinSecureDBEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean calowinSecureDBEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("calowinSecureDBDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.Account") // Entity package for CalowinSecure
                .persistenceUnit("calowinSecureDB")
                .build();
    }

    @Primary
    @Bean(name = "calowinSecureDBTransactionManager")
    public PlatformTransactionManager calowinSecureDBTransactionManager(
            @Qualifier("calowinSecureDBEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
