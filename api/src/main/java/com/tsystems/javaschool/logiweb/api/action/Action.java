/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Igor Avdeev on 9/2/16.
 */
public interface Action {
    void execute(HttpServletRequest req, HttpServletResponse resp)
            throws IOException;
}
