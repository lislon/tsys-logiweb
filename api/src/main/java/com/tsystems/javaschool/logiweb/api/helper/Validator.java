/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.helper;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Igor Avdeev on 8/31/16.
 */
public class Validator {
    private final HttpServletRequest request;
    private Map<String, Class<?>> fields;
    private boolean isValid = false;
    private boolean isValidated = false;

    public class Constraint {
        private String field;
        private String message;

        public Constraint(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {

            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public Validator(Map<String, Class<?>> variables, HttpServletRequest req) {
        this.fields = variables;
        this.request = req;
    }

    public Collection<Constraint> validate() {
        LinkedList<Constraint> constraints = new LinkedList<>();
        for (String name : fields.keySet()) {
            String value = request.getParameter(name);
            if (value == null || value.trim().length() == 0) {
                constraints.add(
                        new Constraint(name, String.format("Argument '{0}' is required", name))
                );
                continue;
            }
            try {
                if (fields.get(name) == Integer.class) {
                    Integer.parseInt(name);
                }
                else if (fields.get(name) == Double.class) {
                    Double.parseDouble(name);
                } else if (fields.get(name).isEnum()) {
                    fields.get(name).getEnumConstants();
                    // TODO Check enum
                } else {
                    throw new IllegalStateException("Type of '" + name + "' field is not supported yet");
                }
            } catch (NumberFormatException e) {
                constraints.add(
                        new Constraint(name, String.format("Argument '{0}' wrong format integer", name))
                );
            }
        }

        isValidated = true;
        isValid = constraints.size() == 0;

        return constraints;
    }

    public boolean isValid()
    {
        if (!isValidated) {
            validate();
        }
        return isValid;
    }

    public int getInteger(String name)
    {
        if (!isValidated) {
            validate();
        }
        if (!isValid) {
            throw new NumberFormatException("Can't get value '"
                    + name + "'. Form is not valid.");
        }
        return Integer.parseInt(name);
    }

    public String getString(String name)
    {
        if (!isValidated) {
            validate();
        }
        if (!isValid) {
            throw new NumberFormatException("Can't get value '"
                    + name + "'. Form is not valid.");
        }
        return request.getParameter(name);
    }

    public <T extends Enum<T>> T getEnum(String name, Class<T> typeclass)
    {
        return Enum.valueOf(typeclass, request.getParameter(name));
    }

}
