package com.epam.controller;

import com.epam.model.Bill;
import com.epam.model.Card;
import com.epam.model.User;
import com.epam.service.BillService;
import com.epam.service.CardService;
import com.epam.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
    public String welcome(Principal principal) {
        return "welcome";
    }

    /**
     * Handling logging in system.
     * @param error - error massage when logging is not successful
     * @param logout - logout massage when user log out from system
     * @return - return model of page which must see user
     */
    @RequestMapping(value = "/login**", method = RequestMethod.GET)
    public ModelAndView login(Principal principal,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }
        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        if (principal == null) {
            model.setViewName("login");
        } else {
            model.setViewName("welcome");
        }
        return model;
    }

    /**
     * Handling user access to not allowed page.
     * @param principal - contains information about authorized user
     * @return model of error page or model of welcome page if user not login
     */
    @RequestMapping(value = "/403**", method = RequestMethod.GET)
    public ModelAndView accesssDenied(Principal principal) {
        ModelAndView model = new ModelAndView();
        if (principal != null) {
            model.addObject("username", principal.getName());
            model.setViewName("403");
        } else {
            model.setViewName("welcome");
        }
        return model;
    }

    /**
     * Prepare admin room page.
     * @return model of admin room
     */
    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
    public ModelAndView adminHomePage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("adminHomePage");
        model.addObject("clients", userService.getAll()); //userService.getAllUnDeletedClients());
        return model;
    }

    /**
     * /**
     * Prepare client room page.
     * @param principal - data about user
     * @return model of client room
     */
    @RequestMapping(value = "/client**", method = RequestMethod.GET)
    public ModelAndView clientHomePage(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        ModelAndView model = new ModelAndView();
        model.setViewName("clientHomePage");
        List<Card> cardList = new ArrayList(new ArrayList<Bill>(user.getBills()).get(0).getCards());
        model.addObject("cards", cardList);
        return model;
    }

    /**
     * Handling operations with client bill
     * @param actionAndBillId - contains bill id and action which must be done
     * @return model of admin room with updated data
     */
    @RequestMapping(value = "/actionWithClientBill", method = RequestMethod.POST)
    public ModelAndView releaseClientBill(@RequestParam("actionAndBillId") String actionAndBillId) {
        billService.doAction(actionAndBillId);
        ModelAndView model = new ModelAndView("redirect:" + "/admin");
        //model.setViewName("adminHomePage");
        //model.addObject("clients", userService.getAll()); //userService.getAllUnDeletedClients());
        return model;
    }
}
