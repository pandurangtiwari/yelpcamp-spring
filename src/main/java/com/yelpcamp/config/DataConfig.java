package com.yelpcamp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.yelpcamp.repository"})
public class DataConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        final String PROPERTY_URL = "url";
        final String PROPERTY_USERNAME = "user";
        final String PROPERTY_PASSWORD = "password";
        final String PROPERTY_DRIVER = "driver";

        var config = new HikariConfig();
        config.setJdbcUrl(environment.getProperty(PROPERTY_URL));
        config.setUsername(environment.getProperty(PROPERTY_USERNAME));
        config.setPassword(environment.getProperty(PROPERTY_PASSWORD));
        config.setDriverClassName(environment.getProperty(PROPERTY_DRIVER));
        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean lfb = new LocalContainerEntityManagerFactoryBean();
        lfb.setDataSource(dataSource());
        lfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        lfb.setPackagesToScan("com.yelpcamp.model");
        lfb.setJpaProperties(hibernateProps());
        return lfb;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    private Properties hibernateProps() {
        final String PROPERTY_DIALECT = "hibernate.dialect";
        final String PROPERTY_SHOW_SQL = "hibernate.show_sql";
        Properties properties = new Properties();
        properties.setProperty(PROPERTY_DIALECT, environment.getProperty(PROPERTY_DIALECT));
        properties.setProperty(PROPERTY_SHOW_SQL, environment.getProperty(PROPERTY_SHOW_SQL));
        return properties;
    }
}
