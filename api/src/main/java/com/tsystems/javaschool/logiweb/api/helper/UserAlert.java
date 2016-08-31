/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.helper;

import jdk.internal.dynalink.support.TypeUtilities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Igor Avdeev on 8/29/16.
 */
public class UserAlert {

    public final static String ATTR_NAME = "alert";

    public enum Type { DANGER, WARNING }

    private Type type;
    private String message;

    private UserAlert(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    public static void injectInRequest(HttpServletRequest req, String message)
    {
        injectInRequest(req, message, Type.WARNING);
    }

    public static void injectInRequest(HttpServletRequest req, String message, UserAlert.Type type) {
        UserAlert alert = new UserAlert(type, message);
        req.getSession().setAttribute(UserAlert.ATTR_NAME, alert);
    }

}
