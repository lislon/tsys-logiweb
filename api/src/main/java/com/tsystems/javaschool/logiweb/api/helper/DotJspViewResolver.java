/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.helper;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Resolve views using . as separator
 */
public class DotJspViewResolver extends InternalResourceViewResolver {
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        // replace all dots with slashes
        return super.buildView(viewName.replaceAll("\\.", "/"));
    }
}
