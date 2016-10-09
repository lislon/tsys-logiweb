/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Igor Avdeev on 9/24/16.
 */
@Controller
@RequestMapping("/hello")
public class TestController {

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        uiModel.addAttribute("name", "Lisa");
        return "hello";
    }
}
