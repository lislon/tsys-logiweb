/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.servlet.truck;

import com.tsystems.javaschool.logiweb.api.action.Action;
import com.tsystems.javaschool.logiweb.api.helper.ServicesFacade;
import com.tsystems.javaschool.logiweb.api.helper.UserAlert;
import com.tsystems.javaschool.logiweb.dao.entities.Truck;
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
        ServicesFacade managersFacade = ((ServicesFacade)req.getAttribute("servicesFacade"));
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

            int id = 0;
            if (req.getParameter("id") != null) {
                id = Integer.parseInt(req.getParameter("id"));
            }


            TruckManager.DTO truck = new TruckManager.DTO(
                    id,
                    req.getParameter("name"),
                    Integer.parseInt(req.getParameter("maxDrivers")),
                    Integer.parseInt(req.getParameter("capacityKg")),
                    Truck.Condition.valueOf(req.getParameter("condition")),
                    Integer.parseInt(req.getParameter("cityId"))
            );

            managersFacade.beginTransaction();
            try {
                truckManager.save(truck);
                managersFacade.commitTransaction();
            } catch (Exception e){
                managersFacade.rollbackTransaction();
                throw e;
            }

            UserAlert.injectInSession(req, req.getParameter("name") + " truck is saved", UserAlert.Type.SUCCESS);

            resp.sendRedirect(req.getContextPath() + "/truck/list.do");

        } catch (Exception e) {
//            UserAlert.injectInRequest(req, "Parameter parsing error: " + e,
//                    UserAlert.Type.DANGER);
//
//            renderEditForm(req, managersFacade);
            throw e;
        }
    }
}
