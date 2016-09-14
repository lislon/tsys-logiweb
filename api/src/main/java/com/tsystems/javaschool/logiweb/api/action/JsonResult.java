/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.jms.ObjectMessage;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by Igor Avdeev on 9/12/16.
 */
public abstract class JsonResult {

    abstract void write(HttpServletResponse resp) throws IOException;

    public static JsonResult success() {
        return new JsonResult() {
            @Override
            void write(HttpServletResponse resp) throws IOException {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("success", true);
                Json.createWriter(resp.getWriter()).writeObject(objectBuilder.build());
            }
        };
    }

    public static JsonResult wrongArgs(String message) {
        return new JsonResult() {
            @Override
            void write(HttpServletResponse resp) throws IOException {
                resp.setStatus(400);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("errorMessage", message);
                Json.createWriter(resp.getWriter()).writeObject(objectBuilder.build());

            }
        };
    }

    public static JsonResult error(String message) {
        return new JsonResult() {
            @Override
            void write(HttpServletResponse resp) throws IOException {
                resp.setStatus(500);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("errorMessage", message);
                Json.createWriter(resp.getWriter()).writeObject(objectBuilder.build());

            }
        };
    }

    public static JsonResult list(Collection<?> collection) {
        return JsonResult.object(collection);
    }

    public static JsonResult object(Object object) {
        return new JsonResult() {
            @Override
            void write(HttpServletResponse resp) throws IOException {
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(resp.getWriter(), object);
            }
        };
    }
}
