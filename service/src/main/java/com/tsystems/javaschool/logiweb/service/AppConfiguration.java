/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

//@Configuration
//@EnableTransactionManagement
//@ComponentScan({ "com.tsystems.javaschool.logiweb.dao", "com.tsystems.javaschool.logiweb.service" })
public class AppConfiguration {

    @Bean
    public DriverManagerDataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

//            <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect" />
//            <property name="hibernate.connection.driver_class" value="org.h2.Driver" />
//            <property name="hibernate.connection.url" value="jdbc:h2:target/test;MODE=MySQL" />
//            <property name="hibernate.hbm2ddl.auto" value="create" />
//            <property name="hibernate.hbm2ddl.import_filess" value="sql/result.sql" />

        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:target/test;MODE=MySQL");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager() {

        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory()
                .getNativeEntityManagerFactory());
        transactionManager.setDataSource(dataSource());
        transactionManager.setJpaDialect(jpaDialect());

        return transactionManager;
    }

    @Bean
    public HibernateJpaDialect jpaDialect() {
        return new HibernateJpaDialect();
    }

    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendor = new HibernateJpaVendorAdapter();

        jpaVendor.setDatabase(Database.H2);
        jpaVendor.setDatabasePlatform("org.hibernate.dialect.H2Dialect");

        return jpaVendor;

    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory
                .setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
        entityManagerFactory.setPersistenceUnitName("persistence");
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactory.setJpaDialect(jpaDialect());

        return entityManagerFactory;

    }

}
