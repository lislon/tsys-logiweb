/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.helper;

import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class FlashMessageSender {
    /**
     * Redirects to "list" action of controller and flashes ${message}.
     * @param redirectToController Controller to which redirection is done
     * @param redirectAttributes Request attributes for adding flash message
     * @param message Body of flash message
     * @return View name to be returned from controller
     */
    public static String redirectToListWithSuccess(Class<?> redirectToController,
                                                   RedirectAttributes redirectAttributes, String message) {
        redirectAttributes.addFlashAttribute("successAlert", message);
        return "redirect:" +  MvcUriComponentsBuilder.fromMethodName(redirectToController, "list").build();
    }
}
