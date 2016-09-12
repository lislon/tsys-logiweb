/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.json.truck;

import com.tsystems.javaschool.logiweb.api.action.JsonAction;
import com.tsystems.javaschool.logiweb.api.action.JsonResult;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Igor Avdeev on 9/2/16.
 */
public class TruckDeleteAction extends JsonAction {

    @Override
    protected JsonResult doAction(HttpServletRequest req, ServiceContainer managers) {

        int id = Integer.parseInt(req.getParameter("id"));

        if (managers.getTruckManager().delete(id)) {
            return JsonResult.success();
        }
        return JsonResult.error("Entry not found");
    }
}
