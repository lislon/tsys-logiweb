/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.helper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Igor Avdeev on 8/23/16.
 */
public class MyEmFactory implements ServletContextListener {

    private static EntityManagerFactory emf = null;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initializeFactory();
    }

    public static void initializeFactory()
    {
        if (emf != null)
            throw new IllegalStateException("Factory already created");
        emf = Persistence.createEntityManagerFactory("mypersistence");
    }

    public static CriteriaBuilder createCriteriaBuilder()
    {
        if (emf == null)
            throw new IllegalStateException("Factory is not yet created");
        return emf.getCriteriaBuilder();
    }

    public static EntityManager createEntityManager()
    {
        if (emf == null)
            throw new IllegalStateException("Factory is not yet created");
        return emf.createEntityManager();
    }

    public static void closeFactory()
    {
        assert (emf != null);
        if (emf != null) {
            emf.close();
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        closeFactory();
    }
}
