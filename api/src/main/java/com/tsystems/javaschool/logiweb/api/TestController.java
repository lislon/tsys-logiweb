/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Igor Avdeev on 9/24/16.
 */
@RequestMapping("/hello")
@Controller
public class TestController {
    public String list(Model uiModel) {
        uiModel.addAttribute("name", "Lisa");
        return "hello";
    }
}
