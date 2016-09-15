/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.servlet.cargo;

import com.tsystems.javaschool.logiweb.api.action.Action;
import com.tsystems.javaschool.logiweb.api.helper.RenderHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Igor Avdeev on 9/15/16.
 */
public class ShowFormCargoAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RenderHelper.renderTemplate("logiweb.cargoes.list", req, resp);
    }
}
