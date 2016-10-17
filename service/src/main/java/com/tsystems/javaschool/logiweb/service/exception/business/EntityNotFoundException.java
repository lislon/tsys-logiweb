/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.exception.business;

/**
 * Created by Igor Avdeev on 9/12/16.
 */
public class EntityNotFoundException extends BusinessLogicException {

    public EntityNotFoundException(String entity, int id) {
        super("Enity " + entity + " with id '" + id + "' not found");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
