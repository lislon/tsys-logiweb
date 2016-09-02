/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.api.truck;

import com.tsystems.javaschool.logiweb.api.action.Action;
import com.tsystems.javaschool.logiweb.api.helper.JsonHelper;
import com.tsystems.javaschool.logiweb.api.helper.RenderHelper;
import com.tsystems.javaschool.logiweb.api.helper.ServicesFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Igor Avdeev on 9/2/16.
 */
public class EditDeleteAction implements Action {

    final static Logger logger = LoggerFactory.getLogger(EditDeleteAction.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServicesFacade servicesFacade = ((ServicesFacade)req.getAttribute("servicesFacade"));
        servicesFacade.beginTransaction();
        boolean isEntryFound;

        try {
            int id = Integer.parseInt(req.getParameter("id"));

            isEntryFound = servicesFacade.getTruckManager().delete(id);

            if (isEntryFound) {
                servicesFacade.commitTransaction();
                JsonHelper.sendSuccess(resp);
            } else {
                servicesFacade.rollbackTransaction();
                resp.sendError(404);
            }

        } catch (Exception e) {
            logger.error("Failed to delete truck " + req.getParameter("id"), e);
            servicesFacade.rollbackTransaction();
            JsonHelper.sendError(resp, "Error while deleting entry");
        }
    }
}
