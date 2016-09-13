/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.servlet.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.javaschool.logiweb.api.helper.RenderHelper;
import com.tsystems.javaschool.logiweb.service.ServiceContainer;
import com.tsystems.javaschool.logiweb.service.dto.OrderCargoDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;

/**
 * Created by Igor Avdeev on 9/6/16.
 */
public class EditHelper {
    /**
     * Injects truck data into a req for JSP page. Source is depends on current
     * @param req
     * @param facade
     */
    public static void renderEditForm(HttpServletRequest req, HttpServletResponse resp,
                                      ServiceContainer facade) throws IOException {


        RenderHelper.renderTemplate("logiweb.order.edit", req, resp);
    }
}
