package com.toby.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

@Configuration
public class UserDaoFactory {
    @Bean
    public UserDao awsUserDao() {
        return new UserDao(awsDataSource());
    }
    @Bean
    public UserDao localUserDao() {
        return new UserDao(localDataSource());
    }

    @Bean
    DataSource awsDataSource(){
        Map<String, String> env = System.getenv();
        SimpleDriverDataSource datasource = new SimpleDriverDataSource();
        datasource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        datasource.setUrl(env.get("DB_HOST"));
        datasource.setUsername(env.get("DB_USER"));
        datasource.setPassword(env.get("DB_PASSWORD"));
        return datasource;
    }
    @Bean
    DataSource localDataSource(){
        Map<String, String> env = System.getenv();
        SimpleDriverDataSource datasource = new SimpleDriverDataSource();
        datasource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        datasource.setUrl(env.get("DB_HOST"));
        datasource.setUsername(env.get("DB_USER"));
        datasource.setPassword(env.get("DB_PASSWORD"));
        return datasource;
    }
}
