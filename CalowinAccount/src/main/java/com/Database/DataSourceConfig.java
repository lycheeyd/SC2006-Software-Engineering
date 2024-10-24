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
    @Bean(name = "calowinSecureDBDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.calowinsecuredb")
    public DataSource calowinSecureDBDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "calowinDBDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.calowindb")
    public DataSource calowinDBDataSource() {
        return DataSourceBuilder.create().build();
    }
}
