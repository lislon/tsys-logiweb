/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action;

import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by Igor Avdeev on 9/12/16.
 */
public abstract class JsonAction implements Action {

    final static Logger logger = LoggerFactory.getLogger(JsonAction.class);

    abstract protected JsonResult doAction(HttpServletRequest req, ServiceContainer managers) throws IOException;

     @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json; charset=UTF-8");
        try {
            JsonResult result = this.doAction(req, ((ServiceContainer) req.getAttribute("serviceContainer")));
            result.write(resp);
        } catch (IOException e) {
            JsonResult.error(e.getMessage()).write(resp);
            logger.warn("Error on request: " + req.getRequestURI(), e);
            throw e;
        } catch (Exception e) {
            JsonResult.error(e.getMessage()).write(resp);
            logger.error("Error on request: " + req.getRequestURI(), e);
        }
    }
}
