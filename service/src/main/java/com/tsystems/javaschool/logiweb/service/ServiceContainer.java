/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service;

import com.tsystems.javaschool.logiweb.service.impl.CityManagerImpl;
import com.tsystems.javaschool.logiweb.service.impl.DriverManagerImpl;
import com.tsystems.javaschool.logiweb.service.impl.OrderManagerImpl;
import com.tsystems.javaschool.logiweb.service.impl.TruckManagerImpl;
import com.tsystems.javaschool.logiweb.service.manager.CityManager;
import com.tsystems.javaschool.logiweb.service.manager.DriverManager;
import com.tsystems.javaschool.logiweb.service.manager.OrderManager;
import com.tsystems.javaschool.logiweb.service.manager.TruckManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Igor Avdeev on 9/12/16.
 */
public class ServiceContainer implements ApplicationContextAware {

    private ApplicationContext appContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }

    private ApplicationContext context;

    public ServiceContainer(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public TruckManager getTruckManager()
    {
        return context.getBean(TruckManagerImpl.class);
    }

    public CityManager getCityManager()
    {
        return context.getBean(CityManagerImpl.class);
    }

    public OrderManager getOrderManager()
    {
        return context.getBean(OrderManagerImpl.class);
    }

    public DriverManager getDriverManager()
    {
        return context.getBean(DriverManagerImpl.class);
    }

}
