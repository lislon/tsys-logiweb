/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.test;
import java.io.InputStream;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.HibernateException;
import org.hibernate.internal.SessionImpl;
import org.junit.After;

public class BaseDBUnitSetup {

    private static IDatabaseConnection connection;
    private static IDataSet dataset;

    @PersistenceUnit(name = "logiweb-test")
    public EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @PostConstruct
    public void init() throws HibernateException, DatabaseUnitException,
            SQLException {

        entityManager = entityManagerFactory.createEntityManager();

        connection = new DatabaseConnection(
                ((SessionImpl) (entityManager.getDelegate())).connection());
        connection.getConfig().setProperty(
                DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
                new HsqldbDataTypeFactory());

        FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
        InputStream dataSet = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("test-data.xml");
        dataset = flatXmlDataSetBuilder.build(dataSet);

        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

    }

    @After
    public void afterTests() {
        entityManager.clear();
    }

}
