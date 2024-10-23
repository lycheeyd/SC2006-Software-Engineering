package com.Database;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name = "CalowinSecureDBDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.calowinsecuredb")
    public DataSource userDbDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "CalowinDBDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.calowindb")
    public DataSource userDetailsDbDataSource() {
        return DataSourceBuilder.create().build();
    }
}
