/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service;

import com.tsystems.javaschool.logiweb.dao.helper.TransactionProxy;
import com.tsystems.javaschool.logiweb.dao.repos.CityRepository;
import com.tsystems.javaschool.logiweb.dao.repos.DriverRepository;
import com.tsystems.javaschool.logiweb.dao.repos.OrderRepository;
import com.tsystems.javaschool.logiweb.dao.repos.TruckRepository;
import com.tsystems.javaschool.logiweb.service.impl.CityManagerImpl;
import com.tsystems.javaschool.logiweb.service.impl.DriverManagerImpl;
import com.tsystems.javaschool.logiweb.service.impl.OrderManagerImpl;
import com.tsystems.javaschool.logiweb.service.impl.TruckManagerImpl;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import com.tsystems.javaschool.logiweb.service.manager.OrderManager;
import com.tsystems.javaschool.logiweb.service.manager.TruckManager;

import javax.persistence.EntityManager;

/**
 * Created by Igor Avdeev on 9/12/16.
 */
public class ServiceContainer {

    private EntityManager em;
    private TransactionProxy transactionProxy;

    public ServiceContainer(EntityManager em) {
        this.em = em;
        this.transactionProxy = new TransactionProxy(em);
    }

    public TruckManager getTruckManager()
    {
        return (TruckManager)transactionProxy.createProxy(new TruckManagerImpl(new TruckRepository(em), this));
    }

    public CityManager getCityManager()
    {
        return (CityManager)transactionProxy.createProxy(new CityManagerImpl(new CityRepository(em), this));
    }

    public OrderManager getOrderManager()
    {
        return (OrderManager)transactionProxy.createProxy(new OrderManagerImpl(new OrderRepository(em), this));
    }

    public DriverManager getDriverManager()
    {
        return (DriverManager)transactionProxy.createProxy(new DriverManagerImpl(new DriverRepository(em), this));
    }

}
