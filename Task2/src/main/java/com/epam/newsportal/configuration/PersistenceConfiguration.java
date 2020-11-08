package com.epam.newsportal.configuration;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.util.Properties;

import static org.hibernate.cfg.Environment.*;

@Configuration
public class PersistenceConfiguration {

    @Value("${session.factory.packages.to.scan}") private String scan;
    @Value("${data.source.username}") private String username;
    @Value("${data.source.password}") private String password;
    @Value("${data.source.url}") private String url;
    @Value("${data.source.driver_class}") private String driver;
    @Value("${hibernate.dialect}") private String dialect;
    @Value("${hibernate.hbm2ddl.auto}") private String auto;
    @Value("${hibernate.show_sql}") private String show;

    @Bean
    @DependsOn("flyway")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setPackagesToScan(scan);
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }

    @Bean
    public Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(DIALECT, dialect);
        hibernateProperties.setProperty(HBM2DDL_AUTO, auto);
        hibernateProperties.setProperty(SHOW_SQL, show);
        return hibernateProperties;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Flyway flyway() {
        ClassicConfiguration configuration = new ClassicConfiguration();
        configuration.setDataSource(dataSource());
        Flyway flyway = new Flyway(configuration);
        flyway.migrate();
        return flyway;
    }
}
