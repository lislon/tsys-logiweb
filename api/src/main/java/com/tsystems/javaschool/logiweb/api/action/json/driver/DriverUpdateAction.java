/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.json.driver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.tsystems.javaschool.logiweb.api.action.JsonAction;
import com.tsystems.javaschool.logiweb.api.action.JsonResult;
import com.tsystems.javaschool.logiweb.dao.entities.Driver;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.service.exception.BusinessLogicException;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Igor Avdeev on 9/13/16.
 */
public class DriverUpdateAction extends JsonAction {

    @AllArgsConstructor
    private static class DriverData {
        public String firstName;
        public String lastName;
        public String personalCode;
        public int hoursWorked;
        public Driver.Status status;
        public int cityId;
    }

    @Override
    protected JsonResult doAction(HttpServletRequest req, ServiceContainer managers) throws IOException {
        Driver driver = new Driver();

        ObjectMapper mapper = new ObjectMapper();
        DriverData data = mapper.readValue(req.getReader(), DriverData.class);

        driver.setFirstName(data.firstName);
        driver.setLastName(data.lastName);
        driver.setHoursWorked(data.hoursWorked);
        driver.setStatus(data.status);
        driver.setPersonalCode(data.personalCode);


        if (req.getParameter("id") != null && req.getParameter("id").trim().length() > 0) {
            driver.setId(Integer.parseInt(req.getParameter("id")));
        }
        try {
            managers.getDriverManager().save(driver, data.cityId, null);
        } catch (BusinessLogicException e) {
            return JsonResult.error(e.getMessage());
        }

        return JsonResult.success();
    }
}
