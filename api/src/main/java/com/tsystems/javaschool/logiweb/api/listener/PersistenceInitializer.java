/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.listener;

import com.tsystems.javaschool.logiweb.dao.helper.LocalEntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Igor Avdeev on 8/23/16.
 */
public class PersistenceInitializer implements ServletContextListener {

    final static Logger logger = LoggerFactory.getLogger(PersistenceInitializer.class);

    private static EntityManagerFactory emf = null;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("Application started");
        LocalEntityManagerFactory.initializeFactory();
    }

    public static CriteriaBuilder createCriteriaBuilder()
    {
        if (emf == null)
            throw new IllegalStateException("Factory is not yet created");
        return emf.getCriteriaBuilder();
    }


    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LocalEntityManagerFactory.closeFactory();
    }
}
