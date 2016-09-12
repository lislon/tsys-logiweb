/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.json.driver;

import com.tsystems.javaschool.logiweb.api.action.JsonAction;
import com.tsystems.javaschool.logiweb.api.action.JsonResult;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.dao.entities.Driver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Igor Avdeev on 9/3/16.
 */
public class DriverListAction extends JsonAction {

    @Override
    protected JsonResult doAction(HttpServletRequest req, ServiceContainer managers) {
        List<Driver> drivers = managers.getDriverManager().findAll();
        return JsonResult.list(drivers);
    }
}
