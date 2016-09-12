/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.exception;

import java.io.IOException;

/**
 * Created by Igor Avdeev on 9/12/16.
 */
public class DuplicateKeyException extends IOException {
    public DuplicateKeyException(String message) {
        super(message);
    }
}
