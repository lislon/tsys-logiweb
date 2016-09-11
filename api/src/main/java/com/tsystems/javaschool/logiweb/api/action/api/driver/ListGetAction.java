/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.api.driver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.logiweb.api.action.Action;
import com.tsystems.javaschool.logiweb.api.helper.ServicesFacade;
import com.tsystems.javaschool.logiweb.service.dto.order.OrderSummaryDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Igor Avdeev on 9/3/16.
 */
public class ListGetAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServicesFacade servicesFacade = ((ServicesFacade)req.getAttribute("servicesFacade"));

        List<OrderSummaryDTO> allOrdersSummary =
                servicesFacade.getOrderManager().getAllOrdersSummary();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(), allOrdersSummary);
    }
}
