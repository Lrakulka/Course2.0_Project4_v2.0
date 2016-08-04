package com.epam.controller;

import com.epam.model.Bill;
import com.epam.model.Card;
import com.epam.model.User;
import com.epam.service.BillService;
import com.epam.service.CardService;
import com.epam.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
        model.addObject("clients", userService.getAllClientsWithBills());
        return model;
    }

    /**
     * Handling operations with client bill
     * @param actionAndBillId - contains bill id and action which must be done
     * @return redirect to admin room with updated data
     */
    @RequestMapping(value = "/actionWithClientBill", method = RequestMethod.POST)
    public ModelAndView clientBill(@RequestParam("actionAndCardId") String actionAndBillId,
                                   Principal principal) {
        User user = userService.findByEmail(principal.getName());
        if (user.getActive()) {
            billService.doAction(actionAndBillId);
        }
        return new ModelAndView("redirect:" + "/admin");
    }

    /**
     * /**
     * Prepare client room page.
     * @param principal - data about authorized user
     * @return model of client room
     */
    @RequestMapping(value = "/client**", method = RequestMethod.GET)
    public ModelAndView clientHomePage(Principal principal) throws Exception {
        User user = userService.findByEmail(principal.getName());
        ModelAndView model = new ModelAndView();
        model.setViewName("clientHomePage");
        model.addObject("bills", billService.getAllClientBills(user));
        return model;
    }

    /**
     * Handling operations with client bill
     * @param actionAndCardId - contains card id and action which must be done
     * @return redirect to client room with updated data
     */
    @RequestMapping(value = "/actionWithClientCard", method = RequestMethod.POST)
    public ModelAndView clientCard(@RequestParam("actionAndCardId") String actionAndCardId,
                                   Principal principal) {
        User user = userService.findByEmail(principal.getName());
        if (user.getActive()) {
            cardService.doAction(actionAndCardId);
        }
        return new ModelAndView("redirect:" + "/client");
    }

    /**
     * Handling request of client's bill filling with money
     * @param billId - id of card which connected with bill
     * @param money - money count to fill bill
     * @param principal - data about user which want to fill his bill
     * @return model of client room with updated data
     */
    @RequestMapping(value = "/fillClientBill", method = RequestMethod.POST)
    public ModelAndView fillClientBill(@RequestParam("billId") Integer billId,
                                       @RequestParam("moneyCount") Double money,
                                       Principal principal) {
        User user = userService.findByEmail(principal.getName());
        ModelAndView modelAndView = new ModelAndView("redirect:" + "/client");
        billService.fillBill(user, billId, money);
        return modelAndView;
    }

    /**
     * Handling request of client's bill payment
     * @param billId - id of card which connected with bill
     * @param payment - money count to make payment
     * @param cardName - name of card which will except money
     * @param password - password of one of the bills card
     * @param principal - data about user which want to make payment
     * @return model of client room with updated data
     */
    @RequestMapping(value = "/sentMoney", method = RequestMethod.POST)
    public ModelAndView makeClientPayment(@RequestParam("billId") Integer billId,
                                          @RequestParam("moneyCount") Double payment,
                                          @RequestParam("nativeCardId") String nativeCardId,
                                          @RequestParam("cardName") String cardName,
                                          @RequestParam("passWord") String password,
                                          Principal principal) {
        User user = userService.findByEmail(principal.getName());
        ModelAndView modelAndView = new ModelAndView("redirect:" + "/client");
        Card exceptCard = cardService.findByName(cardName);
        if (exceptCard == null) {
            modelAndView.addObject("msgCard", "Card with such name doesn't exist");
            return modelAndView;
        }
        Bill clientBill = billService.getClientBill(user, billId);
        if (clientBill == null) {
            modelAndView.addObject("msgBill", "Your have no such bill");
            return modelAndView;
        }
        if (billService.checkPassword(password)) {
            modelAndView.addObject("msgPass", "Password error");
            return modelAndView;
        }
        billService.makePayment(clientBill, exceptCard, payment);
        return modelAndView;
    }

    /*@ExceptionHandler(Exception.class)
    public ModelAndView handleIOException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView("brokenPage");
        return modelAndView;
    }*/
}
