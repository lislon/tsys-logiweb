/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.helper;

import com.tsystems.javaschool.logiweb.dao.helper.LocalEntityManagerFactory;
import com.tsystems.javaschool.logiweb.dao.repos.CityRepository;
import com.tsystems.javaschool.logiweb.dao.repos.OrderRepository;
import com.tsystems.javaschool.logiweb.dao.repos.TruckRepository;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import com.tsystems.javaschool.logiweb.service.manager.OrderManager;
import com.tsystems.javaschool.logiweb.service.manager.TruckManager;

import javax.persistence.EntityManager;

/**
 * Single point of creation Manager-like classes with single EntityManager.
 *
 * This class is useful in Servlets methods.
 */
public class ServicesFacade {

    private EntityManager em;

    public ServicesFacade() {
        em = LocalEntityManagerFactory.createEntityManager();
    }

    public TruckManager getTruckManager()
    {
        return new TruckManager(new TruckRepository(em));
    }

    public CityManager getCityManager()
    {
        return new CityManager(new CityRepository(em));
    }

    public OrderManager getOrderManager()
    {
        return new OrderManager(new OrderRepository(em));
    }

    public void beginTransaction()
    {
        em.getTransaction().begin();
    }

    public void commitTransaction()
    {
        em.getTransaction().commit();
    }

    public void rollbackTransaction()
    {
        em.getTransaction().rollback();
    }

    public void closeEm() {
        em.close();
        em = null;
    }
}
