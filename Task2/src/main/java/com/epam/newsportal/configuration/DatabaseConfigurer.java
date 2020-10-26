package com.epam.newsportal.configuration;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.util.Properties;

@Configuration
public class DatabaseConfigurer {
    @Value("${session.factory.packages.to.scan}") private String scan;
    @Value("${data.source.username}") private String username;
    @Value("${data.source.password}") private String password;
    @Value("${data.source.url}") private String url;
    @Value("${data.source.driver_class}") private String driver;
    @Value("${hibernate.dialect}") private String dialect;
    @Value("${hibernate.hbm2ddl.auto}") private String auto;
    @Value("${hibernate.show_sql}") private String show;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setPackagesToScan(scan);
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
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
        hibernateProperties.setProperty(Environment.DIALECT, dialect);
        hibernateProperties.setProperty(Environment.HBM2DDL_AUTO, auto);
        hibernateProperties.setProperty(Environment.SHOW_SQL, show);
        return hibernateProperties;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}
