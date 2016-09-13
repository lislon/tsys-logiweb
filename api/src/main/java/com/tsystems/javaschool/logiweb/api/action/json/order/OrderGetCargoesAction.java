/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.json.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.logiweb.api.action.JsonAction;
import com.tsystems.javaschool.logiweb.api.action.JsonResult;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.service.dto.OrderCargoDTO;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;

/**
 * Created by Igor Avdeev on 9/13/16.
 */
public class OrderGetCargoesAction extends JsonAction {
    @Override
    protected JsonResult doAction(HttpServletRequest req, ServiceContainer managers) throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        Collection<OrderCargoDTO> orderCargoes = managers.getOrderManager().getOrderCargoes(id);
        return JsonResult.list(orderCargoes);
    }
}
