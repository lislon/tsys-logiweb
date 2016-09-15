/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.json.cargo;

import com.tsystems.javaschool.logiweb.api.action.JsonAction;
import com.tsystems.javaschool.logiweb.api.action.JsonResult;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Igor Avdeev on 9/15/16.
 */
public class ListCargoAction extends JsonAction {
    @Override
    protected JsonResult doAction(HttpServletRequest req, ServiceContainer managers) throws IOException {
        return JsonResult.error("not implementd");
    }
}
