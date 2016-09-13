/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.json.order;

import com.tsystems.javaschool.logiweb.api.action.Action;
import com.tsystems.javaschool.logiweb.api.action.JsonAction;
import com.tsystems.javaschool.logiweb.api.action.JsonResult;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Igor Avdeev on 9/6/16.
 */
public class OrderDeleteAction extends JsonAction {
    @Override
    protected JsonResult doAction(HttpServletRequest req, ServiceContainer managers) throws IOException {
        if (managers.getOrderManager().delete(Integer.parseInt(req.getParameter("id")))) {
            return JsonResult.success();
        }
        return JsonResult.error("Order not found");
    }
}
