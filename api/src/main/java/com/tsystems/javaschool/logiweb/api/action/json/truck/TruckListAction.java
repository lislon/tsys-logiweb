/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.json.truck;

import com.tsystems.javaschool.logiweb.api.action.JsonAction;
import com.tsystems.javaschool.logiweb.api.action.JsonResult;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.service.dto.TruckDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Igor Avdeev on 8/28/16.
 */
public class TruckListAction extends JsonAction {

    @Override
    protected JsonResult doAction(HttpServletRequest req, ServiceContainer managers) {
        List<TruckDTO> allTrucks = managers.getTruckManager().findAllTrucks();

        return JsonResult.list(allTrucks);
    }
}
