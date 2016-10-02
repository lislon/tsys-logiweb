/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Igor Avdeev on 9/13/16.
 */
public class SaveOriginalUrlForMenuFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) request).getRequestURI();

        requestURI = requestURI.substring(((HttpServletRequest) request).getContextPath().length());

        request.setAttribute("origin", requestURI);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
