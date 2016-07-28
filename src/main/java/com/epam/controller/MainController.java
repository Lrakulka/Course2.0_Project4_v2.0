package com.epam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by fg on 7/27/2016.
 */

@Controller
public class MainController {
    @RequestMapping("/*")
    public String init() {
        return "index";
    }
}
