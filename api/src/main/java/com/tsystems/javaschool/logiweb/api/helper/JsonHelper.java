/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.helper;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Igor Avdeev on 9/2/16.
 */
public class JsonHelper {
    public static void sendError(HttpServletResponse resp, String message) throws IOException
    {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("errorMessage", message);
        Json.createWriter(resp.getWriter()).writeObject(objectBuilder.build());
    }

    public static void sendSuccess(HttpServletResponse resp) throws IOException
    {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("error", 0);
        Json.createWriter(resp.getWriter()).writeObject(objectBuilder.build());
    }
}
