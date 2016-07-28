package com.epam.controller;

import com.epam.model.User;
import com.epam.service.BillService;
import com.epam.service.CardService;
import com.epam.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * Created by fg on 7/27/2016.
 */

@Controller
public class MainController {
    private static final Logger logger = Logger.getLogger(MainController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;
    @Autowired
    private BillService billService;

    /**
     * Default page.
     * @return model of welcome page
     */
    @RequestMapping(value = "/**")
    public String welcome() {
        return "welcome";
    }
}
