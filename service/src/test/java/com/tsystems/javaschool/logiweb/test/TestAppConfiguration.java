/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Created by Igor Avdeev on 9/28/16.
 */
@Configuration
public class TestAppConfiguration {

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
}
