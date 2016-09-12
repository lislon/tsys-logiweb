/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.dao.helper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Igor Avdeev on 8/27/16.
 */
public class LocalEntityManagerFactory {

    private static EntityManagerFactory emf;

    public synchronized static void initializeFactory()
    {
        if (emf != null)
            throw new IllegalStateException("Factory already created");
        emf = Persistence.createEntityManagerFactory("logiweb");
    }

    public synchronized static void closeFactory()
    {
        if (emf != null) {
            emf.close();
            emf = null;
        }
    }

    public static EntityManager createEntityManager()
    {
        if (emf == null)
            throw new IllegalStateException("Factory is not yet created");
        return emf.createEntityManager();
    }
}
