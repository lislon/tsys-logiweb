/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.exception.business;

/**
 * Created by Igor Avdeev on 9/12/16.
 */
public class DuplicateEntityException extends BusinessLogicException {
    public DuplicateEntityException(String message) {
        super(message);
    }
}
