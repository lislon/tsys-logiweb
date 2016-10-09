/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.servlet.truck;

import com.tsystems.javaschool.logiweb.api.action.Action;
import com.tsystems.javaschool.logiweb.api.helper.UserAlert;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.service.exception.BusinessLogicException;
import com.tsystems.javaschool.logiweb.service.manager.TruckManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * Created by Igor Avdeev on 9/2/16.
 */
public class EditPostAction implements Action {

    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServiceContainer managersFacade = ((ServiceContainer)req.getAttribute("serviceContainer"));
        TruckManager truckManager = managersFacade.getTruckManager();

        try {

            if (!Stream.of("name", "maxDrivers", "capacityKg", "cityId", "condition")
                    .allMatch(name -> req.getParameter(name) != null
                            && req.getParameter(name).trim().length() > 0)) {
                UserAlert.injectInRequest(req, "Not all required fields are set",
                        UserAlert.Type.DANGER);

                EditHelper.renderEditForm(req, resp, managersFacade);
                return;
            }

            int cityId = Integer.parseInt(req.getParameter("cityId"));

            Truck truck = new Truck();
            if (req.getParameter("id") != null) {
                truck.setId(Integer.parseInt(req.getParameter("id")));
            }
            truck.setName(req.getParameter("name"));
            truck.setMaxDrivers(Integer.parseInt(req.getParameter("maxDrivers")));
            truck.setCapacityKg(Integer.parseInt(req.getParameter("capacityKg")));
            truck.setCondition(Truck.Condition.valueOf(req.getParameter("condition")));

            truckManager.save(truck, cityId);

            UserAlert.injectInSession(req, req.getParameter("name") + " truck is saved", UserAlert.Type.SUCCESS);

            resp.sendRedirect(req.getContextPath() + "/truck/list");

        } catch (BusinessLogicException e) {
            UserAlert.injectInRequest(req, e.getMessage(), UserAlert.Type.DANGER);
            EditHelper.renderEditForm(req, resp, managersFacade);
        } catch (Exception e) {
//            UserAlert.injectInRequest(req, "Parameter parsing error: " + e,
//                    UserAlert.Type.DANGER);
//
//            renderEditForm(req, managersFacade);
            throw e;
        }
    }
}
